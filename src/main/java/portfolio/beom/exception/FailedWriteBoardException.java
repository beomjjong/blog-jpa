package portfolio.beom.exception;

public class FailedWriteBoardException extends BeomException {

    private final static String MESSAGE = "게시글 작성 실패";
    public FailedWriteBoardException() {
        super(MESSAGE);
    }

    @Override
    public int statusCode() {
        return 400;
    }
}
