package com.ssafy.api.response;


import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("BaseResponseBody")
public class PgProfileResBody extends BaseResponseBody{
    @ApiModelProperty(name="한줄 프로필", example = "안녕하세요 저는")
    String introduce;
    @ApiModelProperty(name="지역 목록", example = "서울,인천")
    String[] location;

    public static PgProfileResBody of(Integer statusCode, String message, String introduce, String[] location) {
        PgProfileResBody body = new PgProfileResBody();
        body.setStatusCode(statusCode);
        body.setMessage(message);
        body.setIntroduce(introduce);
        body.setLocation(location);
        return body;
    }

}
