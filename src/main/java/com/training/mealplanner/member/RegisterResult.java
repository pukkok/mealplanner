package com.training.mealplanner.member;

public enum RegisterResult {
    SUCCESS("회원가입에 성공하였습니다."),
    USERNAME_EXISTS("이미 사용 중인 아이디입니다."),
    UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.");

    private final String message;

    RegisterResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}