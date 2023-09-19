package com.kh.hellomentor.member.model.dao;

import com.kh.hellomentor.member.model.vo.Payment;
import com.kh.hellomentor.member.model.vo.Profile;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.hellomentor.member.model.vo.Member;

import java.util.List;
import java.util.Map;

@Repository
public class MemberDao {

    @Autowired
    private SqlSessionTemplate sqlSession;

    public Member loginUser(Member m) {
        Member loginUser = sqlSession.selectOne("memberMapper.loginUser", m);
        Profile profile = sqlSession.selectOne("memberMapper.selectProfile", loginUser.getUserNo());
        if (profile != null) {
            loginUser.setProfile(profile.getFilePath() + profile.getChangeName());
        } else {
            loginUser.setProfile("/img/default-profile.jpg");
        }
        return loginUser;
    }

    public int insertMember(Member m) {
        return sqlSession.insert("memberMapper.insertMember", m);
    }

    public List<Member> getFollowList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowList", userNo);
    }


    public List<Profile> getProfileList(int userNo) {
        return sqlSession.selectList("memberMapper.getProfileList", userNo);
    }

    public List<Member> getFollwerList(int userNo) {
        return sqlSession.selectList("memberMapper.getFollowerList", userNo);
    }

    public void updateMember(Member updatedMember) {
        sqlSession.update("memberMapper.updateProfile", updatedMember);
    }





    public int insertUpdateToken(Map<String, Object> tokenData) {
        int selectResult = 0;
        int updateResult = 0;
        int insertResult = 0;

        Member m = (Member) tokenData.get("m");
        Payment payment = (Payment) tokenData.get("payment");

        selectResult = sqlSession.selectOne("memberMapper.selectToken", m.getUserNo());

        if (selectResult >= 0) {
            // member 테이블에 토큰이 update가 됏다면?


            updateResult = sqlSession.update("memberMapper.updateToken", m);

            if (selectResult >= 0 || updateResult > 0) {
                // 토큰이 업데이트되었으면 결제 정보를 추가합니다.
                insertResult = sqlSession.insert("memberMapper.insertPayment", payment);
            }
        }

        // 각 결과를 확인하고 필요한 처리를 수행합니다.
        if (selectResult > 0 && updateResult > 0 && insertResult > 0) {
            return 1; // 성공적으로 모든 작업이 수행됨을 나타냅니다.
        } else {
            return 0; // 작업 중 하나라도 실패한 경우를 나타냅니다.
        }
    }

    public int exchangeToken(Member m) {
        return sqlSession.update("memberMapper.exchangeToken", m);
    }

    public int getUpdateToken(int userNo) {
        return sqlSession.selectOne("memberMapper.selectupdateToken", userNo);
    }

    public int paymentResult(int userNo) {
        return sqlSession.insert("memberMapper.insertPaymentResult",userNo);
    }
}