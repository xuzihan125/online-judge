package com.oj.ojBackend.model.vo;

import cn.hutool.json.JSONUtil;
import com.oj.ojBackend.model.dto.question.JudgeCase;
import com.oj.ojBackend.model.dto.question.JudgeConfig;
import com.oj.ojBackend.model.entity.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * problem
 * @TableName question
 */
@Data
public class QuestionAdminVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * title of the problem
     */
    private String title;

    /**
     * content of the problem
     */
    private String content;

    /**
     * tags of the problem, stored in json format
     */
    private List<String> tags;

    /**
     * answer of the problem
     */
    private String answer;

    /**
     * total time of submit
     */
    private Integer submitNum;

    /**
     * total number of accept
     */
    private Integer acceptNum;

    /**
     * test case.(json format)
     */
    private List<JudgeCase> judgeCase;

    /**
     * test settings(json format)
     */
    private JudgeConfig judgeConfig;

    /**
     * number of likes
     */
    private Integer likeNum;

    /**
     * number of favor
     */
    private Integer favourNum;

    /**
     * user id of the creater
     */
    private Long userId;

    /**
     * time of creation
     */
    private Date createTime;

    /**
     * time of last update
     */
    private Date updateTime;

    /**
     * info about question creator
     */
    private UserVO userVO;

    private static final long serialVersionUID = 1L;

    /**
     * object vo convert to object
     *
     * @param questionVO
     * @return
     */
    public static Question voToObj(QuestionAdminVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<String> tagList = questionVO.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfig voJudgeConfig = questionVO.getJudgeConfig();
        if(voJudgeConfig != null){
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * object convert to object vo
     *
     * @param question
     * @return
     */
    public static QuestionAdminVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionAdminVO questionVO = new QuestionAdminVO();
        BeanUtils.copyProperties(question, questionVO);
        List<String> tags = JSONUtil.toList(question.getTags(), String.class);
        questionVO.setTags(tags);
        String judgeConfigStr = question.getJudgeConfig();
        questionVO.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfig.class));
        return questionVO;
    }
}