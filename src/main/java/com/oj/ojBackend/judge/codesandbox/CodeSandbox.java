package com.oj.ojBackend.judge.codesandbox;

import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRequest;
import com.oj.ojBackend.judge.codesandbox.model.ExecuteCodeRespond;

public interface CodeSandbox {

    ExecuteCodeRespond executeCode(ExecuteCodeRequest executeCodeRequest);
}
