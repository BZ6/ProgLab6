package common.connection;

/**
 * Status enum:
 * ERROR: Not fatal error that user can fix
 * FINE: All right
 * EXIT: Need to stop program
 * CHECK_ID: Need check id for update command
 */
public enum Status {
    ERROR,
    FINE,
    EXIT,
    CHECK_ID;
}
