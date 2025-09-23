package kr.happyjob.study.service.Usermgr;

import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.repository.usermgr.UsermgrreactMapper;
import kr.happyjob.study.vo.usermgr.UserModel;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class UsermgrreactService {

    // Set logger
    private final Logger logger = LogManager.getLogger(this.getClass());

    // Get class name for logger
    private final String className = this.getClass().toString();

    @Value("${fileUpload.rootPath}")
    private String rootPath;

    @Value("${fileUpload.userPath}")
    private String userPath;

    @Value("${fileUpload.virtualRootPath}")
    private String virtualRootPath;


    @Autowired
    private UsermgrreactMapper usermgrreactMapper;

    public List<UserModel> userListreact(Map<String, Object> paramMap) throws Exception {
        return usermgrreactMapper.userListreact(paramMap);
    }

    public int userListreactcnt(Map<String, Object> paramMap) throws Exception {
        return usermgrreactMapper.userListreactcnt(paramMap);
    }

    public UserModel selectuser(Map<String, Object> paramMap) throws Exception {
        return usermgrreactMapper.selectuser(paramMap);
    }

    public int insertuser(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
        logger.info("   - insertuser  !!!!!!!!! " );
        // file
        // 1. upload file 지정된 디렉토리에 저장
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

        String itemFilePath = userPath + File.separator;
        // rootpath : "Z:\\FileRepository"
        // RvirtualRootPath : /servefile
        // 저장 경로 : Z:\\FileRepository\\user\\a.jpg

        // 1. multipartHttpServletRequest upload file 의 blob 와 파일이름
        // 2. 파일을 지정된 디렉토리에 저장
        FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, virtualRootPath, itemFilePath);
        Map<String, Object> fileinfo = fileUtil.uploadFiles();

        // 2. upload file 의 정보를 인출
        //map.put("file_nm", file_nm);      // 파일명
        //map.put("file_size", file_Size);  // 파일 사이즈
        //map.put("file_loc", file_loc);    // 파일 물리경로
        //map.put("vrfile_loc", vrfile_loc);  // 파일 논리 경로
        //map.put("fileExtension", fileExtension);  // 확장자
        //map.put("file_nm_uuid", file_nm_uuid);    // uuid

        // 3. DB 처리(insert)
        // file 첨부 여부
        String fileSizeStr = (String) fileinfo.get("file_size");
        int filesize = 0;

        logger.info("   - filesize  : " + filesize);
        if (fileSizeStr != null && !fileSizeStr.isEmpty()) {
            filesize = Integer.parseInt(fileSizeStr);
            logger.info("   - filesize  : " + filesize);
            paramMap.put("fileexist","Y");
            paramMap.put("fileinfo",fileinfo);
        } else {
            logger.info("   - 파일 없음");
            paramMap.put("fileexist","N");
            paramMap.put("fileinfo",null);
        }
        logger.info("   - insert start   " );
        return usermgrreactMapper.insertuser(paramMap);
    }

    public int updateuser(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

        logger.info("   - update start   " + paramMap);

        paramMap.put("imgread","Y");
        UserModel seleuser = usermgrreactMapper.selectuser(paramMap);

        boolean imgecheck = Boolean.valueOf( (String) paramMap.get("imgecheck"));
        // file
        // 1. 기존 등록된 정보에 파일 존하면 삭제(유지 체크박스가 체크가 안되어 있을때만)
        int fileSize = seleuser.getFilesize();

        logger.info("   - filesize  : " + fileSize);

        if (fileSize > 0) {
            if(!imgecheck) {
                logger.info("   - imgecheck  : " + imgecheck);
                File oldfile = new File(seleuser.getPygicalpath());
                oldfile.delete();
            }
        }

        // 2. upload file 지정된 디렉토리에 저장
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;

        String itemFilePath = userPath + File.separator;
        FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, virtualRootPath, itemFilePath);
        Map<String, Object> fileinfo = fileUtil.uploadFiles();
        logger.info("   - fileinfo  : " + (String) fileinfo.get("file_size"));
        // 3. upload file 의 정보를 인출
        String fileSizeStr = (String) fileinfo.get("file_size");
        int filesize = 0;

        if (fileSizeStr != null && !fileSizeStr.isEmpty()) {
            filesize = Integer.parseInt(fileSizeStr);
            logger.info("   - filesize  : " + filesize);
            logger.info("   - fileinfo.filesize  : " + fileinfo.get("file_size"));
            paramMap.put("fileexist","Y");
            paramMap.put("fileinfo",fileinfo);
        } else {
            logger.info("   - 파일 없음");

            if(imgecheck) {
                paramMap.put("imgecheck","Y");
            } else {
                paramMap.put("imgecheck","N");
            }

            paramMap.put("fileexist","N");
            paramMap.put("fileinfo",null);
        }
        // 4. DB 처리(update)
        return usermgrreactMapper.updateuser(paramMap);
    }

    public int deleteuser(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {

        // file
        // 1. 기존 등록된 정보에 파일 존하면 삭제
        paramMap.put("imgread","Y");
        UserModel seleuser = usermgrreactMapper.selectuser(paramMap);

        int fileSize = seleuser.getFilesize();

        logger.info("   - filesize  : " + fileSize);

        if (fileSize > 0) {
            File oldfile = new File(seleuser.getPygicalpath());
            oldfile.delete();
        }

        // 2. DB 처리 (delete)
        return usermgrreactMapper.deleteuser(paramMap);
    }

    public int dupcheck(Map<String, Object> paramMap) throws Exception {

        // file
        // 1. 기존 등록된 정보에 파일 존하면 삭제
        // 4. DB 처리 (delete)

        return usermgrreactMapper.dupcheck(paramMap);
    }


}