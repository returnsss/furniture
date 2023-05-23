package com.teamproject.furniture.member.dtos;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.teamproject.furniture.member.dtos.QMemberPageDto is a Querydsl Projection type for MemberPageDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QMemberPageDto extends ConstructorExpression<MemberPageDto> {

    private static final long serialVersionUID = 2038047317L;

    public QMemberPageDto(com.querydsl.core.types.Expression<String> userId, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> birth, com.querydsl.core.types.Expression<String> gender, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<String> phone, com.querydsl.core.types.Expression<Integer> state) {
        super(MemberPageDto.class, new Class<?>[]{String.class, String.class, String.class, String.class, String.class, String.class, String.class, int.class}, userId, name, birth, gender, email, address, phone, state);
    }

}

