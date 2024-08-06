package com.codingrecipe.board2024_boot_jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 시간 정보를 다룰 수 있도록 하는 객체

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {

    @CreationTimestamp // 생성 되었을 때 시간을 만들어주는 어노테이션
    @Column(updatable = false) // <-- 수정 시 반영x
    private LocalDateTime createdTime;

    @UpdateTimestamp // update가 발생했을 때 시간을 만들어주는 어노테이션
    @Column(insertable = false) // <-- 입력 시 (insert) 반영 x
    private LocalDateTime updatedTime;
}
