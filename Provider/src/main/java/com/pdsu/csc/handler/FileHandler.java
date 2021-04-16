/*
jenkins真难配，害
 */

package com.pdsu.csc.handler;

import com.github.pagehelper.PageInfo;
import com.pdsu.csc.bean.*;
import com.pdsu.csc.service.FileDownloadService;
import com.pdsu.csc.service.UserInformationService;
import com.pdsu.csc.service.WebFileService;
import com.pdsu.csc.utils.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *  文件相关API
 * @author 半梦
 *	提供文件下载，上传，文件列表等
 */
@RestController
@RequestMapping("/file")
@Log4j2
@SuppressWarnings("unchecked")
public class FileHandler extends InitHandler {
	
	/**
	 * 文件操作相关
	 */
	private WebFileService webFileService;

	/**
	 * 下载相关
	 */
	private FileDownloadService fileDownloadService;

	/**
	 * 用户相关
	 */
	private UserInformationService userInformationService;
	
	/**
	 * 
	 * @param file  上传的文件
	 * @param title  文件名称
	 * @param description  文件描述
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result upload(@RequestParam("file")MultipartFile file, @RequestParam String title,
						 @RequestParam String description, UserInformation user) throws Exception{
		loginOrNotLogin(user);
		Integer uid = user.getUid();
		log.info("用户: " + uid + " 上传文件: " + title + " 开始" + ", 描述为:" + description);
		byte [] s = file.getBytes();
		String name = HashUtils.getFileNameForHash(title) + StringUtils.getSuffixName(file.getOriginalFilename());
		log.info("文件名为: " + name);
		FileUtils.writeByteArrayToFile(new File(fileFilePath + name), s);
		log.info("文件写入成功, 开始在服务器保存地址");
		WebFile webFile = new WebFile(uid, title, description, name, DateUtils.getSimpleDateSecond());
		boolean b = webFileService.insert(webFile);
		if(b) {
			log.info("上传成功");
			return Result.success().add("fileid", webFile.getId());
		}
		log.error("上传失败");
		return Result.fail().add(EXCEPTION, NETWORK_BUSY);
	}
	
	/**
	 * 下载文件
	 * @param uid 作者 uid
	 * @param title 文件名
	 * @param response HttpServletResponse
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public Result download(@RequestParam Integer uid, @RequestParam String title, HttpServletResponse response,
						   UserInformation user) throws Exception{
		OutputStream out = null;
		InputStream in = null;
		loginOrNotLogin(user);
		try {
			log.info("开始下载文件, 下载人 UID 为: " + user.getUid());
			log.info("查询文件是否存在");
			WebFile webfile = webFileService.selectFileByUidAndTitle(uid, title);
			String filePath = webfile.getFilePath();
			String url = fileFilePath + filePath;
			in = new FileInputStream(url);
			response.setContentType("multipart/form-data");
			String filename = title + "_" + uid + StringUtils.getSuffixName(filePath);
			response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + "\"");
			out = response.getOutputStream();
			byte [] bytes = new byte[in.available()];
			in.read(bytes);
			out.write(bytes);
			out.flush();
			fileDownloadService.insert(new FileDownload(webfile.getId(), uid, user.getUid()));
			log.info("下载成功");
		} finally {
			if(!Objects.isNull(out)) {
				try {
					out.close();
				} catch (IOException e) {
					log.warn("输出流关闭失败");
				}
			}
			if(!Objects.isNull(in)) {
				try {
					in.close();
				} catch (IOException e) {
					log.warn("输入流关闭失败");
				}
			}
		}
		return Result.success();
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	@GetMapping("/getfileindex")
	public Result getFileIndex(@RequestParam(defaultValue = "1") Integer p) throws Exception{
		log.info("获取首页文件");
		List<WebFile> list = webFileService.selectFilesOrderByTime(p);
		List<Integer> uids = new ArrayList<Integer>();
		List<Integer> fileids = new ArrayList<Integer>();
		for(WebFile file : list) {
			uids.add(file.getUid());
			fileids.add(file.getId());
		}
		log.info("获取作者信息");
		List<UserInformation> users = userInformationService.selectUsersByUids(uids);
		log.info("获取文件下载量");
		List<Integer> downloads = fileDownloadService.selectDownloadsByFileIds(fileids);
		List<FileInformation> files = new ArrayList<FileInformation>();
		for (int i = 0; i < list.size(); i++) {
			FileInformation fileInformation = new FileInformation();
			fileInformation.setWebfile(list.get(i));
			fileInformation.setDownloads(downloads.get(i));
			for (UserInformation user : users) {
				UserInformation u = user;
				user.setPassword(null);
				if(user.getUid().equals(list.get(i).getUid())) {
					fileInformation.setUser(user);
					break;
				}
			}
			files.add(fileInformation);
		}
		PageInfo<WebFile> fileList = new PageInfo<>(list);
		return Result.success().add("fileList", files).add(HAS_NEXT_PAGE, fileList.isHasNextPage());
	}

	@Autowired
	public FileHandler(WebFileService webFileService,
					   FileDownloadService fileDownloadService,
					   UserInformationService userInformationService) {
		this.webFileService = webFileService;
		this.fileDownloadService = fileDownloadService;
		this.userInformationService = userInformationService;
	}
}
