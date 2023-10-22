package com.oj.ojBackend.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.oj.ojBackend.common.BaseResponse;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.common.ResultUtils;
import com.oj.ojBackend.exception.BusinessException;
import com.oj.ojBackend.judge.JudgeService;
import com.oj.ojBackend.judge.codesandbox.CodeSandbox;
import com.oj.ojBackend.judge.codesandbox.CodeSandboxFactory;
import com.oj.ojBackend.judge.codesandbox.CodeSandboxProxy;
import com.oj.ojBackend.judge.codesandbox.impl.RemoteCodeSandbox;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * question api
 *
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Resource
    private RemoteCodeSandbox remoteCodeSandbox;

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Value("${codesandbox.IP:localhost}")
    private String sandboxIP;

    @Value("${codesandbox.port:8081}")
    private String port;

    @Resource
    private JudgeService judgeService;

    @Value("${codesandbox.type:example}")
    private String type;

    @PostMapping("/test")
    public BaseResponse<String> test() {
        String url = "http://" + sandboxIP + ":" + port + "/check";
        try{
            String respondStr = HttpUtil.createGet(url)
                    .execute()
                    .body();
            if(StringUtils.isBlank(respondStr)) {
                throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
            }
            return ResultUtils.success(respondStr);
        }
        catch (Exception e){
            throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
        }
    }

    @PostMapping("/sandbox")
    public BaseResponse<String> sandbox() {
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setCode("public class Main{\n" +
                "     public static void main(String[] args){\n" +
                "        int a = Integer.valueOf(args[0]);\n" +
                "        int b = Integer.valueOf(args[1]);\n" +
                "        System.out.println(a+b);\n" +
                "    }\n" +
                "}");
        executeCodeRequest.setInputList(Arrays.asList("1 2","3 4"));
        executeCodeRequest.setLanguage("java");

        String url = "http://" + sandboxIP + ":" + port + "/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        try{
            log.info(url);
            String respondStr = HttpUtil.createPost(url)
                    .body(json)
                    .execute()
                    .body();
            if(StringUtils.isBlank(respondStr)) {
                throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
            }
            ExecuteCodeRespond executeCodeRespond = JSONUtil.toBean(respondStr, ExecuteCodeRespond.class);
            return ResultUtils.success(executeCodeRespond.toString());
        }
        catch (Exception e){
            throw new BusinessException(ErrorCode.API_REQUETS_ERROR);
        }
    }

    @PostMapping("/testFunction")
    public BaseResponse<ExecuteCodeRespond> testFunction(){
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
        executeCodeRequest.setCode("public class Main{\n" +
                "     public static void main(String[] args){\n" +
                "        int a = Integer.valueOf(args[0]);\n" +
                "        int b = Integer.valueOf(args[1]);\n" +
                "        System.out.println(a+b);\n" +
                "    }\n" +
                "}");
        executeCodeRequest.setInputList(Arrays.asList("1 2","3 4"));
        executeCodeRequest.setLanguage("java");
        try{
            return ResultUtils.success(remoteCodeSandbox.executeCode(executeCodeRequest));
        }catch (Exception e){
            throw new BusinessException(ErrorCode.API_REQUETS_ERROR, e.getMessage());
        }
    }

    @PostMapping("/testAsync")
    public BaseResponse<String> testAsync(){
        CompletableFuture.runAsync(() ->{
            ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest();
            executeCodeRequest.setCode("public class Main{\n" +
                    "     public static void main(String[] args){\n" +
                    "        int a = Integer.valueOf(args[0]);\n" +
                    "        int b = Integer.valueOf(args[1]);\n" +
                    "        System.out.println(a+b);\n" +
                    "    }\n" +
                    "}");
            executeCodeRequest.setInputList(Arrays.asList("1 2","3 4"));
            executeCodeRequest.setLanguage("java");
            CodeSandbox sandbox = codeSandboxFactory.newInstance(type);
            ExecuteCodeRespond executeCodeRespond = sandbox.executeCode(executeCodeRequest);
            log.info(executeCodeRespond.toString());
        });
        return ResultUtils.success("run async");
    }


}
