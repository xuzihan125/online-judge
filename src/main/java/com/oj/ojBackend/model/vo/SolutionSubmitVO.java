package com.oj.ojBackend.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.oj.ojBackend.model.dto.solutionsubmit.JudgeInfo;
import com.oj.ojBackend.model.entity.SolutionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Data
public class SolutionSubmitVO {
    /**
     * id
     */
    private Long id;

    /**
     * language used for coding
     */
    private String language;

    /**
     * code submitted by user
     */
    private String code;

    /**
     * result of judge. stored in a json format
     */
    private JudgeInfo judgeInfo;

    /**
     * status of judging(0 - to be judged / 1 - judging / 2 - success / 3 - fail)
     */
    private Integer status;

    /**
     * id of the questionId
     */
    private Long questionId;

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

    private static final long serialVersionUID = 1L;
    /**
     * object vo convert to object
     *
     * @param solutionSubmitVO
     * @return
     */
    public static SolutionSubmit voToObj(SolutionSubmitVO solutionSubmitVO) {
        if (solutionSubmitVO == null) {
            return null;
        }
        SolutionSubmit solutionSubmit = new SolutionSubmit();
        BeanUtils.copyProperties(solutionSubmitVO, solutionSubmit);
        JudgeInfo judgeInfo = solutionSubmitVO.getJudgeInfo();
        if(judgeInfo != null){
            solutionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        }
        return solutionSubmit;
    }

    /**
     * object convert to object vo
     *
     * @param solutionSubmit
     * @return
     */
    public static SolutionSubmitVO objToVo(SolutionSubmit solutionSubmit) {
        if (solutionSubmit == null) {
            return null;
        }
        SolutionSubmitVO solutionSubmitVO = new SolutionSubmitVO();
        BeanUtils.copyProperties(solutionSubmit, solutionSubmitVO);
        JudgeInfo judgeInfo = JSONUtil.toBean(solutionSubmit.getJudgeInfo(), JudgeInfo.class);
        solutionSubmitVO.setJudgeInfo(judgeInfo);
        return solutionSubmitVO;
    }
}
