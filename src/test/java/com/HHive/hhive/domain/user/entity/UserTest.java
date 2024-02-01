package com.HHive.hhive.domain.user.entity;

import com.HHive.hhive.domain.category.data.MajorCategory;
import com.HHive.hhive.domain.category.data.SubCategory;
import com.HHive.hhive.domain.user.dto.UpdateUserProfileRequestDTO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("User Entity 테스트")
class UserTest {

    @Nested
    @DisplayName("유저 정보 업데이트 테스트")
    class UpdateUserInfoTest {

        @Test
        @DisplayName("카카오 ID 업데이트")
        void kakaoIdUpdate() {
            // given
            User user = new User("testUser", "testPassword", "testEmail@email.com", "testDescription");
            Long kakaoId = 12345678L;

            // when
            user.kakaoIdUpdate(kakaoId);

            // then
            assertEquals(kakaoId, user.getKakaoId());
        }

        @Test
        @DisplayName("프로필 업데이트")
        void updateProfile() {
            // given
            User user = new User(
                    "testUser", "testPassword", "testEmail@email.com", "testDescription");
            UpdateUserProfileRequestDTO requestDTO = new UpdateUserProfileRequestDTO(
                    "updateEmail@email.com", "updateDescription");

            // when
            user.updateProfile(requestDTO);

            // then
            assertEquals(requestDTO.getEmail(), user.getEmail());
            assertEquals(requestDTO.getDescription(), user.getDescription());
        }

        @Test
        @DisplayName("계정 삭제 업데이트")
        void updateDeletedAt() {
            // given
            User user = new User(
                    "testUser", "testPassword", "testEmail@email.com", "testDescription");
            assertFalse(user.is_deleted());

            // when
            user.updateDeletedAt();

            // then
            assertTrue(user.is_deleted());
            assertNotNull(user.getDeletedAt());
        }
    }

    @Nested
    @DisplayName("유저 정보 설정 테스트")
    class SetUserInfoTest {

        @Test
        @DisplayName("비밀번호 설정")
        void setPassword() {
            // given
            User user = new User(
                    "testUser", "testPassword", "testEmail@email.com", "testDescription");
            String newPassword = "newPassword";

            // when
            user.setPassword(newPassword);

            // then
            assertEquals(newPassword, user.getPassword());
        }

        @Test
        @DisplayName("major 카테고리 설정")
        void setMajorCategory() {
            // given
            User user = new User(
                    "testUser", "testPassword", "testEmail@email.com", "testDescription");
            MajorCategory majorCategory = MajorCategory.GAME;

            // when
            user.setMajorCategory(majorCategory);

            // then
            assertEquals(majorCategory, user.getMajorCategory());
        }

        @Test
        @DisplayName("sub 카테고리 설정")
        void setSubCategory() {
            // given
            User user = new User(
                    "testUser", "testPassword", "testEmail@email.com", "testDescription");
            SubCategory subCategory = SubCategory.LOL;

            // when
            user.setSubCategory(subCategory);

            // then
            assertEquals(subCategory, user.getSubCategory());
        }
    }
}

