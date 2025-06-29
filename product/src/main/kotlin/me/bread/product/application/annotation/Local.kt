package me.bread.product.application.annotation

import org.springframework.context.annotation.Profile

/**
 * 로컬 환경 표시 어노테이션
 * 로컬 개발 환경에서만 활성화되는 컴포넌트를 표시하는 어노테이션
 * Spring의 Profile 기능을 활용하여 "local" 프로필이 활성화된 환경에서만 해당 컴포넌트가 등록된다
 */
@Profile("local")
annotation class Local
