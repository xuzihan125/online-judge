package com.oj.ojBackend.judge.codesandbox;

import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;
import com.oj.ojBackend.model.enums.SolutionLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandboxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        System.out.println(type);
        CodeSandbox sandbox = CodeSandboxFactory.newInstance(type);
        sandbox = new CodeSandboxProxy(sandbox);
        String code = "public class Main {\n" +
                "    public static void main(String[] args){\n" +
                "        int a = Integer.parseInt(args[0]);\n" +
                "        int b = Integer.parseInt(args[1]);\n" +
                "        System.out.println(\"result\"+(a+b));\n" +
                "    }\n" +
                "}\n";
        String language = SolutionLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2","3 4");
        ExecuteCodeRequest request = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeRespond respond = sandbox.executeCode(request);
        System.out.println("respond:"+respond.toString());
        Assertions.assertNotNull(respond);
        Assertions.assertEquals("result3",respond.getOutputList().get(0));
        Assertions.assertEquals("result7",respond.getOutputList().get(1));
    }
}