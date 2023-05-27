package com.teamproject.furniture.board.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDto { // board의 페이징

    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    private String type; // 검색의 종류 t, c, w, tc, tw, twc
    private String keyword;
    private String link;

    /**
     * 검색 조건들을 BoardRepository에서 String[]로 처리하기 때문에 type이라는 문자열을 배열로 변환해주는 기능
     * @return
     */
    public String[] getTypes(){
        if(this.type == null || type.isEmpty()){
            return new String[0];
        }
        return this.type.split("");
    }

    /**
     * 페이징 처리를 위해서 사용하는 Pageable 타입을 반환하는 기능
     * @param props
     * @return
     */
    public Pageable getPageable(String...props){
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    /**
     * 검색 조건과 페이징 조건 등을 문자열로 구성하는 getLink()
     * @return
     */
    public String getLink(){

        if(link != null){
            return link;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);

        if(type != null && type.length() > 0){
            builder.append("&type=" + this.type);
        }
        if(keyword != null){
            try{
                builder.append("&keyword=" + URLEncoder.encode(this.keyword, "UTF-8"));
            }catch (UnsupportedEncodingException e){

            }
        }
        link = builder.toString();

        return link;
    }
}
