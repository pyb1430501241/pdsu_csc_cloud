package com.pdsu.csc.service.fallback;

import com.pdsu.csc.bean.Result;
import com.pdsu.csc.bean.UserInformation;
import com.pdsu.csc.bean.WebInformation;
import com.pdsu.csc.service.ProviderService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author 半梦
 * @create 2020-11-10 15:52
 * 服务降级
 */
@Service
public class ProviderServiceError implements ProviderService {

    private static final String NETWORK_BUSY = "网络繁忙, 请稍候重试";
    private static final String EXCEPTION = "exception";

    private static final Result RESULT = Result.fail().add(EXCEPTION, NETWORK_BUSY);

//    @Override
//    public Result login(String uid, String password, String hit, String code, Integer flag) {
//        return RESULT;
//    }

    @Override
    public Result getCodeForLogin() {
        return RESULT;
    }

    @Override
    public Result getCodeFoyApply(String email, String name) {
        return RESULT;
    }

    @Override
    public Result applyUser(UserInformation user, String token, String code) {
        return RESULT;
    }

    @Override
    public Result loginStatus() {
        return RESULT;
    }

    @Override
    public Result getEmailByUid(Integer uid) {
        return RESULT;
    }

    @Override
    public Result getCodeForRetrieve(String token) {
        return RESULT;
    }

    @Override
    public Result retrieveThePassword(Integer uid, String password, String token, String code) {
        return RESULT;
    }

    @Override
    public Result getModify() {
        return RESULT;
    }

    @Override
    public Result getCodeForModify(String email) {
        return RESULT;
    }

    @Override
    public Result modifyBefore(String token, String code) {
        return RESULT;
    }

    @Override
    public Result modifyForPassword(String password) {
        return RESULT;
    }

    @Override
    public Result like(Integer uid) {
        return RESULT;
    }

    @Override
    public Result disLike(Integer uid) {
        return RESULT;
    }

    @Override
    public Result getLikeStatus(Integer uid) {
        return RESULT;
    }

    @Override
    public Result updateImage(MultipartFile img) {
        return RESULT;
    }

    @Override
    public Result updateUserInformation(UserInformation user) {
        return RESULT;
    }

    @Override
    public Result getOneselfBlobsByUid(Integer p) {
        return RESULT;
    }

    @Override
    public Result getOneselfFans(Integer p) {
        return RESULT;
    }

    @Override
    public Result getOneselfIcons(Integer p) {
        return RESULT;
    }

    @Override
    public Result getBlobsByUid(Integer p, Integer uid) {
        return RESULT;
    }

    @Override
    public Result getFans(Integer p, Integer uid) {
        return RESULT;
    }

    @Override
    public Result getIcons(Integer p, Integer uid) {
        return RESULT;
    }

    @Override
    public Result getEmailByUid() {
        return RESULT;
    }

    @Override
    public Result dataCheck(String data, String type) {
        return RESULT;
    }

    @Override
    public Result getBrowsingRecord(Integer p) {
        return RESULT;
    }

    @Override
    public Result getOneselfNotification(Integer p) {
        return RESULT;
    }

    @Override
    public Result getWebIndex(Integer p, Integer lid) {
        return RESULT;
    }

    @Override
    public Result getBlobInformation(Integer id) {
        return RESULT;
    }

    @Override
    public Result collection(Integer bid, Integer webid) {
        return RESULT;
    }

    @Override
    public Result deCollection(Integer webid) {
        return RESULT;
    }

    @Override
    public Result collectionStatus(Integer webid) {
        return RESULT;
    }

    @Override
    public Result insert(WebInformation web, List<Integer> labelList) {
        return RESULT;
    }

    @Override
    public Result delete(Integer webid) {
        return RESULT;
    }

    @Override
    public Result update(WebInformation web, List<Integer> labelList) {
        return RESULT;
    }

    @Override
    public Result postComment(Integer webid, String content) {
        return RESULT;
    }

    @Override
    public Result postCommentReply(Integer webid, Integer cid, Integer bid, String content) {
        return RESULT;
    }

    @Override
    public Result getAuthorByUid(Integer uid) {
        return RESULT;
    }

    @Override
    public Result getCollectionByUid(Integer uid, Integer p) {
        return null;
    }

    @Override
    public Result thumbs(Integer webid, Integer bid) {
        return RESULT;
    }

    @Override
    public Result dethumbs(Integer webid) {
        return RESULT;
    }

    @Override
    public Result thumbsStatus(Integer webid) {
        return RESULT;
    }

    @Override
    public Result postBlobImg(MultipartFile img) {
        return RESULT;
    }

    @Override
    public Result getLabel(Integer p) {
        return RESULT;
    }

    @Override
    public Result getContype() {
        return RESULT;
    }

    @Override
    public Result upload(MultipartFile file, String title, String description) {
        return RESULT;
    }

    @Override
    public Result download(Integer uid, String title) {
        return RESULT;
    }

    @Override
    public Result getFileIndex(Integer p) {
        return RESULT;
    }

    @Override
    public Result index() {
        return RESULT;
    }

    @Override
    public Result searchByText(String text) {
        return RESULT;
    }

    @Override
    public Result logout() {
        return RESULT;
    }
}
