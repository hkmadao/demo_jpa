package org.hkmadao.core.advanquery.operators.comparison;

import java.util.concurrent.ConcurrentHashMap;

public class OperatorFactor {

    private static ConcurrentHashMap<String, IOperator> operatorMap = new ConcurrentHashMap<>();

    static {
        operatorMap.put(BetweenOperator.getInstance().getOperatorCode(), BetweenOperator.getInstance());
        operatorMap.put(EqualOperator.getInstance().getOperatorCode(), EqualOperator.getInstance());
        operatorMap.put(GreaterThanOperator.getInstance().getOperatorCode(), GreaterThanOperator.getInstance());
        operatorMap.put(GreaterThanOrEqualOperator.getInstance().getOperatorCode(), GreaterThanOrEqualOperator.getInstance());
        operatorMap.put(InOperator.getInstance().getOperatorCode(), InOperator.getInstance());
        operatorMap.put(LessThanOperator.getInstance().getOperatorCode(), LessThanOperator.getInstance());
        operatorMap.put(LessThanOrEqualToOperator.getInstance().getOperatorCode(), LessThanOrEqualToOperator.getInstance());
        operatorMap.put(LikeOperator.getInstance().getOperatorCode(), LikeOperator.getInstance());
        operatorMap.put(LikeLeftOperator.getInstance().getOperatorCode(), LikeLeftOperator.getInstance());
        operatorMap.put(LikeRightOperator.getInstance().getOperatorCode(), LikeRightOperator.getInstance());
        operatorMap.put(NotEqualOperator.getInstance().getOperatorCode(), NotEqualOperator.getInstance());
        operatorMap.put(NotInOperator.getInstance().getOperatorCode(), NotInOperator.getInstance());
        operatorMap.put(NotNullOperator.getInstance().getOperatorCode(), NotNullOperator.getInstance());
        operatorMap.put(NullOperator.getInstance().getOperatorCode(), NullOperator.getInstance());
    }

    public static IOperator getOperator(String operatorCode) {
        return operatorMap.get(operatorCode);
    }

}
