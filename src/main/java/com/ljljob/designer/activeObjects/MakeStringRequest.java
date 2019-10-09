package com.ljljob.designer.activeObjects;

/**
 * @Author: wujianmin
 * @Date: 2019/9/29 15:07
 * @Function:
 * @Version 1.0
 */
public class MakeStringRequest extends MethodRequest<String> {

    private final Servant servant;

    private final FutureResult futureResult;

    private final int count;

    private final char c;


    public MakeStringRequest(Servant servant, FutureResult<String> futureResult, int count, char c) {
        super(servant, futureResult);
        this.servant = servant;
        this.futureResult = futureResult;
        this.c = c;
        this.count = count;
    }

    @Override
    public void execute() {
        Result result = servant.makeString(this.count, this.c);
        futureResult.done(result);
    }
}
