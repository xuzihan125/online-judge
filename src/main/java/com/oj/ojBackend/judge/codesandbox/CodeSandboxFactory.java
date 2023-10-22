package com.oj.ojBackend.judge.codesandbox;

import com.oj.ojBackend.judge.codesandbox.impl.ExampleCodeSandbox;
import com.oj.ojBackend.judge.codesandbox.impl.RemoteCodeSandbox;
import com.oj.ojBackend.judge.codesandbox.impl.ThirdPartyCodeSandbox;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class CodeSandboxFactory {

    @Resource
    private ExampleCodeSandbox exampleCodeSandbox;

    @Resource
    private RemoteCodeSandbox remoteCodeSandbox;

    @Resource
    private ThirdPartyCodeSandbox thirdPartyCodeSandbox;


    public CodeSandbox newInstance(String type) {
        switch (type){
            case "example":
                return exampleCodeSandbox;
            case "Remote":
                return remoteCodeSandbox;
            case "ThirdParty":
                return thirdPartyCodeSandbox;
            default:
                return exampleCodeSandbox;
        }
    }
}
