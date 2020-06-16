package com.github.aop.advisor;

import com.github.aop.pointcut.AspectJExpressionPointcut;
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
