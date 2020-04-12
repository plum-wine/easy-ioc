package com.github.aop;

import lombok.Data;
import org.aopalliance.aop.Advice;

/**
 * @author plum-wine
 */
@Data
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();

    private Advice advice;

    public void setExpression(String expression) {
        this.pointcut.setExpression(expression);
    }

}
