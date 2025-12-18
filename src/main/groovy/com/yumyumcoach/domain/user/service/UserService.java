package com.yumyumcoach.domain.user.service;

import com.yumyumcoach.domain.auth.entity.Account;
import com.yumyumcoach.domain.auth.mapper.AccountMapper;
import com.yumyumcoach.domain.user.dto.MyTitleResponse;
import com.yumyumcoach.domain.user.dto.MyPageResponse;
import com.yumyumcoach.domain.user.dto.UpdateMyHealthInfoRequest;
import com.yumyumcoach.domain.user.entity.Profile;
import com.yumyumcoach.domain.user.mapper.FollowMapper;
import com.yumyumcoach.domain.user.mapper.ProfileMapper;
import com.yumyumcoach.domain.user.mapper.UserTitleMapper;
import com.yumyumcoach.global.exception.BusinessException;
import com.yumyumcoach.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final AccountMapper accountMapper;
    private final ProfileMapper profileMapper;
    private final FollowMapper followMapper;
    private final UserTitleMapper userTitleMapper;

    public MyPageResponse getMyPage(String email) {

        Account account = accountMapper.findByEmail(email);
        if (account == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }
        Long userId = accountMapper.findIdByEmail(email);

        Profile profile = profileMapper.findByEmail(email);
        if (profile == null) {
            throw new BusinessException(ErrorCode.PROFILE_NOT_FOUND);
        }

        long followers = followMapper.countFollowers(email);
        long followings = followMapper.countFollowings(email);

        MyTitleResponse current = userTitleMapper.findCurrentTitle(email);
        List<MyPageResponse.TitleItem> myTitles = userTitleMapper.findMyTitles(email);

        return MyPageResponse.builder()
                .basic(MyPageResponse.Basic.builder()
                        .userId(userId)
                        .email(email)
                        .username(account.getUsername())
                        .profileImageUrl(profile.getProfileImageUrl())
                        .introduction(profile.getIntroduction())
                        .build())
                .health(MyPageResponse.Health.builder()
                        .birthDate(profile.getBirthDate())
                        .height(profile.getHeight())
                        .weight(profile.getCurrentWeight())
                        .goalWeight(profile.getTargetWeight())
                        .hasDiabetes(profile.getHasDiabetes())
                        .hasHypertension(profile.getHasHypertension())
                        .hasHyperlipidemia(profile.getHasHyperlipidemia())
                        .otherDisease(profile.getOtherDisease())
                        .goal(profile.getGoal())
                        .activityLevel(profile.getActivityLevel())
                        .build())
                .badges(MyPageResponse.Badges.builder()
                        .currentTitleId(current.getCurrentTitleId())
                        .currentTitleName(current.getCurrentTitleName())
                        .titles(myTitles)
                        .build())
                .follow(MyPageResponse.Follow.builder()
                        .followersCount(followers)
                        .followingsCount(followings)
                        .build())
                .build();
    }

    @Transactional
    public void updateMyHealthInfo(String email, UpdateMyHealthInfoRequest req) {
        if (req == null || !req.hasAnyValue()) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST);
        }

        Profile profile = Profile.builder()
                .email(email)
                .birthDate(req.getBirthDate())
                .height(req.getHeight())
                .currentWeight(req.getWeight())
                .targetWeight(req.getGoalWeight())
                .hasDiabetes(req.getHasDiabetes())
                .hasHypertension(req.getHasHypertension())
                .hasHyperlipidemia(req.getHasHyperlipidemia())
                .otherDisease(req.getOtherDisease())
                .goal(req.getGoal())
                .activityLevel(req.getActivityLevel())
                .build();

        profileMapper.updateHealth(profile);
    }

    @Transactional
    public MyTitleResponse selectMyTitle(String email, Long titleId) {

        if (!userTitleMapper.ownsTitle(email, titleId)) {
            throw new BusinessException(ErrorCode.USER_TITLE_NOT_FOUND);
        }

        int updated = profileMapper.updateDisplayTitle(email, titleId);
        if (updated == 0) {
            throw new BusinessException(ErrorCode.PROFILE_NOT_FOUND);
        }

        return userTitleMapper.findCurrentTitle(email);
    }
}
