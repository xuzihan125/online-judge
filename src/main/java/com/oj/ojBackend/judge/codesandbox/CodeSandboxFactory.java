package com.oj.ojBackend.judge.codesandbox;

import com.oj.ojBackend.judge.codesandbox.impl.ExampleCodeSandbox;
import com.oj.ojBackend.judge.codesandbox.impl.RemoteCodeSandbox;
import com.oj.ojBackend.judge.codesandbox.impl.ThirdPartyCodeSandbox;

public class CodeSandboxFactory {
    
    public static CodeSandbox newInstance(String type) {
        switch (type){
            case "example":
                return new ExampleCodeSandbox();
            case "Remote":
                return new RemoteCodeSandbox();
            case "ThirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
