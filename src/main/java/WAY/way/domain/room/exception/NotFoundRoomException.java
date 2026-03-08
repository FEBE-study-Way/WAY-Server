package WAY.way.domain.room.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class NotFoundRoomException extends GlobalException {
    public NotFoundRoomException() {
        super(ErrorCode.NOT_FOUND_ROOM);
    }
}
