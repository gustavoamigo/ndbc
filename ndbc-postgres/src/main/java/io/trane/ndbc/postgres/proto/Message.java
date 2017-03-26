package io.trane.ndbc.postgres.proto;

import java.util.Arrays;

interface Message {

  /** Identifies the message as an authentication request. */
  interface AuthenticationRequest extends BackendMessage {

    /** Specifies that a clear-text password is required. */
    static final class AuthenticationCleartextPassword implements AuthenticationRequest {
      @Override
      public int hashCode() {
        return getClass().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return obj instanceof AuthenticationCleartextPassword;
      }

      @Override
      public String toString() {
        return "AuthenticationCleartextPassword []";
      }
    }

    /** Specifies that GSSAPI authentication is required. */
    static final class AuthenticationGSS implements AuthenticationRequest {
      @Override
      public int hashCode() {
        return getClass().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return obj instanceof AuthenticationGSS;
      }

      @Override
      public String toString() {
        return "AuthenticationGSS []";
      }
    }

    /** Specifies that this message contains GSSAPI or SSPI data. */
    static final class AuthenticationGSSContinue implements AuthenticationRequest {
      /** GSSAPI or SSPI authentication data. */
      public final byte[] authenticationData;

      public AuthenticationGSSContinue(byte[] authenticationData) {
        this.authenticationData = authenticationData;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(authenticationData);
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof AuthenticationGSSContinue))
          return false;
        AuthenticationGSSContinue other = (AuthenticationGSSContinue) obj;
        if (!Arrays.equals(authenticationData, other.authenticationData))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "AuthenticationGSSContinue [authenticationData=" + Arrays.toString(authenticationData) + "]";
      }
    }

    /** Specifies that Kerberos V5 authentication is required. */
    static final class AuthenticationKerberosV5 implements AuthenticationRequest {
      @Override
      public int hashCode() {
        return getClass().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return obj instanceof AuthenticationKerberosV5;
      }

      @Override
      public String toString() {
        return "AuthenticationKerberosV5 []";
      }
    }

    /** Specifies that an MD5-encrypted password is required. */
    static final class AuthenticationMD5Password implements AuthenticationRequest {
      /** The salt to use when encrypting the password. */
      public final byte[] salt;

      public AuthenticationMD5Password(byte[] salt) {
        this.salt = salt;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(salt);
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof AuthenticationMD5Password))
          return false;
        AuthenticationMD5Password other = (AuthenticationMD5Password) obj;
        if (!Arrays.equals(salt, other.salt))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "AuthenticationMD5Password [salt=" + Arrays.toString(salt) + "]";
      }
    }

    /** Specifies that the authentication was successful. */
    static final class AuthenticationOk implements AuthenticationRequest {
      @Override
      public int hashCode() {
        return getClass().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return obj instanceof AuthenticationOk;
      }

      @Override
      public String toString() {
        return "AuthenticationOk []";
      }
    }

    /** Specifies that an SCM credentials message is required. */
    static final class AuthenticationSCMCredential implements AuthenticationRequest {
      @Override
      public int hashCode() {
        return getClass().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return obj instanceof AuthenticationSCMCredential;
      }

      @Override
      public String toString() {
        return "AuthenticationSCMCredential []";
      }
    }

    /** Specifies that SSPI authentication is required. */
    static final class AuthenticationSSPI implements AuthenticationRequest {
      @Override
      public int hashCode() {
        return getClass().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
        return obj instanceof AuthenticationSSPI;
      }

      @Override
      public String toString() {
        return "AuthenticationSSPI []";
      }
      
    }
  }

  /** Identifies the message as cancellation key data. The frontend 
   * must save these values if it wishes to be able to issue CancelRequest 
   * messages later. */
  static final class BackendKeyData implements BackendMessage {
    /** The process ID of this backend. */
    public final int processId;

    /** The secret key of this backend. */
    public final int secretKey;

    public BackendKeyData(int processId, int secretKey) {
      this.processId = processId;
      this.secretKey = secretKey;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + processId;
      result = prime * result + secretKey;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof BackendKeyData))
        return false;
      BackendKeyData other = (BackendKeyData) obj;
      if (processId != other.processId)
        return false;
      if (secretKey != other.secretKey)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "BackendKeyData [processId=" + processId + ", secretKey=" + secretKey + "]";
    }
  }

  interface BackendMessage extends Message {
  }

  /** Identifies the message as a Bind command. */
  static final class Bind implements FrontendMessage {

    /** The name of the destination portal (an empty string selects 
     * the unnamed portal). */
    public final String destinationPortalName;

    /** The number of parameter values that follow (possibly zero). This 
     * must match the number of parameters needed by the query.
     * Next, the following pair of fields appear for each parameter: 
     * The value of the parameter, in the format indicated by the associated 
     * format code. n is the above length.*/
    public final byte[][] fields;

    /** The number of parameter format codes that follow (denoted C below). 
     * This can be zero to indicate that there are no parameters or that 
     * the parameters all use the default format (text); or one, in which 
     * case the specified format code is applied to all parameters; or it 
     * can equal the actual number of parameters.
     * 
     * The parameter format codes. Each must presently be zero (text) or 
     * one (binary). */
    public final short[] parameterFormatCodes;

    /** The result-column format codes. Each must presently be zero (text) 
     * or one (binary). */
    public final short[] resultColumnFormatCodes;

    /** The name of the source prepared statement (an empty string 
     * selects the unnamed prepared statement). */
    public final String sourcePreparedStatementName;

    public Bind(String destinationPortalName, String sourcePreparedStatementName, short[] parameterFormatCodes,
        byte[][] fields, short[] resultColumnFormatCodes) {
      this.destinationPortalName = destinationPortalName;
      this.sourcePreparedStatementName = sourcePreparedStatementName;
      this.parameterFormatCodes = parameterFormatCodes;
      this.fields = fields;
      this.resultColumnFormatCodes = resultColumnFormatCodes;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((destinationPortalName == null) ? 0 : destinationPortalName.hashCode());
      result = prime * result + Arrays.deepHashCode(fields);
      result = prime * result + Arrays.hashCode(parameterFormatCodes);
      result = prime * result + Arrays.hashCode(resultColumnFormatCodes);
      result = prime * result + ((sourcePreparedStatementName == null) ? 0 : sourcePreparedStatementName.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof Bind))
        return false;
      Bind other = (Bind) obj;
      if (destinationPortalName == null) {
        if (other.destinationPortalName != null)
          return false;
      } else if (!destinationPortalName.equals(other.destinationPortalName))
        return false;
      if (!Arrays.deepEquals(fields, other.fields))
        return false;
      if (!Arrays.equals(parameterFormatCodes, other.parameterFormatCodes))
        return false;
      if (!Arrays.equals(resultColumnFormatCodes, other.resultColumnFormatCodes))
        return false;
      if (sourcePreparedStatementName == null) {
        if (other.sourcePreparedStatementName != null)
          return false;
      } else if (!sourcePreparedStatementName.equals(other.sourcePreparedStatementName))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Bind [destinationPortalName=" + destinationPortalName + ", fields=" + Arrays.toString(fields)
          + ", parameterFormatCodes=" + Arrays.toString(parameterFormatCodes) + ", resultColumnFormatCodes="
          + Arrays.toString(resultColumnFormatCodes) + ", sourcePreparedStatementName=" + sourcePreparedStatementName
          + "]";
    }
  }

  static final class BindComplete implements BackendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof BindComplete;
    }

    @Override
    public String toString() {
      return "BindComplete []";
    }
  }

  static final class CancelRequest implements FrontendMessage {
    /** The process ID of the target backend. */
    public final int processId;

    /** The secret key for the target backend. */
    public final int secretKey;

    public CancelRequest(int processId, int secretKey) {
      this.processId = processId;
      this.secretKey = secretKey;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + processId;
      result = prime * result + secretKey;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CancelRequest))
        return false;
      CancelRequest other = (CancelRequest) obj;
      if (processId != other.processId)
        return false;
      if (secretKey != other.secretKey)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CancelRequest [processId=" + processId + ", secretKey=" + secretKey + "]";
    }
  }

  /** Identifies the message as a Close command. */
  static abstract class Close implements FrontendMessage {

    static final class ClosePortal extends Close {
      public ClosePortal(String name) {
        super(name);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof ClosePortal))
          return false;
        ClosePortal other = (ClosePortal) obj;
        if (!name.equals(other.name))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "ClosePortal [name=" + name + "]";
      }
    }

    static final class ClosePreparedStatement extends Close {
      public ClosePreparedStatement(String name) {
        super(name);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof ClosePreparedStatement))
          return false;
        ClosePreparedStatement other = (ClosePreparedStatement) obj;
        if (!name.equals(other.name))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "ClosePreparedStatement [name=" + name + "]";
      }
    }

    /** The name of the prepared statement or portal to close (an empty 
     * string selects the unnamed prepared statement or portal). */
    public final String name;

    public Close(String name) {
      this.name = name;
    }
  }

  static final class CloseComplete implements BackendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof CloseComplete;
    }

    @Override
    public String toString() {
      return "CloseComplete []";
    }
  }

  /** Identifies the message as a command-completed response. */
  static abstract class CommandComplete implements BackendMessage {

    /** For a COPY command, the tag is COPY rows where rows is the 
     * number of rows copied. (Note: the row count appears only in 
     * PostgreSQL 8.2 and later.) */
    static final class CopyComplete extends CommandComplete {
      public CopyComplete(int rows) {
        super(rows);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof CopyComplete))
          return false;
        CopyComplete other = (CopyComplete) obj;
        if (rows != other.rows)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "CopyComplete [rows=" + rows + "]";
      }
    }

    /** For a DELETE command, the tag is DELETE rows where rows is 
     * the number of rows deleted. */
    static final class DeleteComplete extends CommandComplete {
      public DeleteComplete(int rows) {
        super(rows);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof DeleteComplete))
          return false;
        DeleteComplete other = (DeleteComplete) obj;
        if (rows != other.rows)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "DeleteComplete [rows=" + rows + "]";
      }
    }

    /** For a FETCH command, the tag is FETCH rows where rows is the 
     * number of rows that have been retrieved from the cursor. */
    static final class FetchComplete extends CommandComplete {
      public FetchComplete(int rows) {
        super(rows);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof FetchComplete))
          return false;
        FetchComplete other = (FetchComplete) obj;
        if (rows != other.rows)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "FetchComplete [rows=" + rows + "]";
      }
    }

    /** For an INSERT command, the tag is INSERT oid rows, where 
     * rows is the number of rows inserted. oid is the object ID 
     * of the inserted row if rows is 1 and the target table has 
     * OIDs; otherwise oid is 0. */
    static final class InsertComplete extends CommandComplete {
      public final String oid;

      public InsertComplete(int rows, String oid) {
        super(rows);
        this.oid = oid;
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        result = prime * result + ((oid == null) ? 0 : oid.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof InsertComplete))
          return false;
        InsertComplete other = (InsertComplete) obj;
        if (rows != other.rows)
          return false;
        if (!(oid.equals(other.oid)))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "InsertComplete [oid=" + oid + "]";
      }
    }

    /** For a MOVE command, the tag is MOVE rows where rows is the 
     * number of rows the cursor's position has been changed by. */
    static final class MoveComplete extends CommandComplete {
      public MoveComplete(int rows) {
        super(rows);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof MoveComplete))
          return false;
        MoveComplete other = (MoveComplete) obj;
        if (rows != other.rows)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "MoveComplete [rows=" + rows + "]";
      }
    }

    /** For a SELECT or CREATE TABLE AS command, the tag is SELECT 
     * rows where rows is the number of rows retrieved. */
    static final class SelectorOrCreateTableAsCompleted extends CommandComplete {
      public SelectorOrCreateTableAsCompleted(int rows) {
        super(rows);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof SelectorOrCreateTableAsCompleted))
          return false;
        SelectorOrCreateTableAsCompleted other = (SelectorOrCreateTableAsCompleted) obj;
        if (rows != other.rows)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "SelectorOrCreateTableAsCompleted [rows=" + rows + "]";
      }
    }

    /** For an UPDATE command, the tag is UPDATE rows where rows is 
     * the number of rows updated. */
    static final class UpdateComplete extends CommandComplete {
      public UpdateComplete(int rows) {
        super(rows);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + rows;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof UpdateComplete))
          return false;
        UpdateComplete other = (UpdateComplete) obj;
        if (rows != other.rows)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "UpdateComplete [rows=" + rows + "]";
      }
    }

    public final int rows;

    public CommandComplete(int rows) {
      this.rows = rows;
    }
  }

  /** Identifies the message as a Start Copy Both response. 
   * This message is used only for Streaming Replication. */
  static final class CopyBothResponse implements BackendMessage {

    /** The format codes to be used for each column. Each must 
     * presently be zero (text) or one (binary). All must be 
     * zero if the overall copy format is textual. */
    public final short[] columnFormatCodes;

    public CopyBothResponse(short[] columnFormatCodes) {
      this.columnFormatCodes = columnFormatCodes;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(columnFormatCodes);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CopyBothResponse))
        return false;
      CopyBothResponse other = (CopyBothResponse) obj;
      if (!Arrays.equals(columnFormatCodes, other.columnFormatCodes))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CopyBothResponse [columnFormatCodes=" + Arrays.toString(columnFormatCodes) + "]";
    }
  }

  /** Identifies the message as COPY data. */
  static final class CopyData implements FrontendMessage, BackendMessage {
    /** Data that forms part of a COPY data stream. Messages sent 
     * from the backend will always correspond to single data rows, 
     * but messages sent by frontends might divide the data stream 
     * arbitrarily. */
    public final byte[] data;

    public CopyData(byte[] data) {
      this.data = data;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(data);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CopyData))
        return false;
      CopyData other = (CopyData) obj;
      if (!Arrays.equals(data, other.data))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CopyData [data=" + Arrays.toString(data) + "]";
    }
  }

  /** Identifies the message as a COPY-complete indicator. */
  static final class CopyDone implements FrontendMessage, BackendMessage {
    
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof CopyDone;
    }

    @Override
    public String toString() {
      return "CopyDone []";
    }
  }

  /** Identifies the message as a COPY-failure indicator. */
  static final class CopyFail implements FrontendMessage {
    /** An error message to report as the cause of failure. */
    public final String errorMessage;

    public CopyFail(String errorMessage) {
      this.errorMessage = errorMessage;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CopyFail))
        return false;
      CopyFail other = (CopyFail) obj;
      if (errorMessage == null) {
        if (other.errorMessage != null)
          return false;
      } else if (!errorMessage.equals(other.errorMessage))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CopyFail [errorMessage=" + errorMessage + "]";
    }
  }

  /** Identifies the message as a Start Copy In response. The 
   * frontend must now send copy-in data (if not prepared to do 
   * so, send a CopyFail message). */
  static final class CopyInResponse implements BackendMessage {

    /** The format codes to be used for each column. Each must 
     * presently be zero (text) or one (binary). All must be zero 
     * if the overall copy format is textual. */
    public final short[] columnFormatCodes;

    public CopyInResponse(short[] columnFormatCodes) {
      this.columnFormatCodes = columnFormatCodes;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(columnFormatCodes);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CopyInResponse))
        return false;
      CopyInResponse other = (CopyInResponse) obj;
      if (!Arrays.equals(columnFormatCodes, other.columnFormatCodes))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CopyInResponse [columnFormatCodes=" + Arrays.toString(columnFormatCodes) + "]";
    }
  }

  /** Identifies the message as a Start Copy Out response. 
   * This message will be followed by copy-out data. */
  static final class CopyOutResponse implements BackendMessage {

    /** The format codes to be used for each column. Each must 
     * presently be zero (text) or one (binary). All must be 
     * zero if the overall copy format is textual. */
    public final short[] columnFormatCodes;

    public CopyOutResponse(short[] columnFormatCodes) {
      this.columnFormatCodes = columnFormatCodes;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(columnFormatCodes);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof CopyOutResponse))
        return false;
      CopyOutResponse other = (CopyOutResponse) obj;
      if (!Arrays.equals(columnFormatCodes, other.columnFormatCodes))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "CopyOutResponse [columnFormatCodes=" + Arrays.toString(columnFormatCodes) + "]";
    }
  }

  /** Identifies the message as a data row. */
  static final class DataRow implements BackendMessage {
    /** The value of the column, in the format indicated by the 
     * associated format code. n is the above length. */
    public final byte[] columns;

    public DataRow(byte[] columns) {
      this.columns = columns;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(columns);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof DataRow))
        return false;
      DataRow other = (DataRow) obj;
      if (!Arrays.equals(columns, other.columns))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "DataRow [columns=" + Arrays.toString(columns) + "]";
    }
  }

  /** Identifies the message as a Describe command. */
  static abstract class Describe implements FrontendMessage {

    static final class DescribePortal extends Describe {
      public DescribePortal(String name) {
        super(name);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof DescribePortal))
          return false;
        DescribePortal other = (DescribePortal) obj;
        if (name == null) {
          if (other.name != null)
            return false;
        } else if (!name.equals(other.name))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "DescribePortal [name=" + name + "]";
      }
    }

    static final class DescribePreparedStatement extends Describe {
      public DescribePreparedStatement(String name) {
        super(name);
      }
      
      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof DescribePreparedStatement))
          return false;
        DescribePreparedStatement other = (DescribePreparedStatement) obj;
        if (name == null) {
          if (other.name != null)
            return false;
        } else if (!name.equals(other.name))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "DescribePreparedStatement [name=" + name + "]";
      }
    }

    /** The name of the prepared statement or portal to describe 
     * (an empty string selects the unnamed prepared statement 
     * or portal). */
    public final String name;

    public Describe(String name) {
      this.name = name;
    }
  }

  /** Identifies the message as a response to an empty query string. 
   * (This substitutes for CommandComplete.) */
  static final class EmptyQueryResponse implements BackendMessage {
    
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof EmptyQueryResponse;
    }

    @Override
    public String toString() {
      return "EmptyQueryResponse []";
    }
  }

  /** Identifies the message as an error. */
  static final class ErrorResponse implements BackendMessage {
    static final class Field {
      /** A code identifying the field type; if zero, this is the message 
       * terminator and no string follows. The presently defined field 
       * types are listed in Section 51.6. Since more field types might 
       * be added in future, frontends should silently ignore fields of 
       * unrecognized type. */
      public final char type;

      /** The field value. */
      public final String value;

      public Field(char type, String value) {
        this.type = type;
        this.value = value;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + type;
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof Field))
          return false;
        Field other = (Field) obj;
        if (type != other.type)
          return false;
        if (value == null) {
          if (other.value != null)
            return false;
        } else if (!value.equals(other.value))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "Field [type=" + type + ", value=" + value + "]";
      }
    }

    /** The message body consists of one or more identified fields, followed 
     * by a zero byte as a terminator. Fields can appear in any order. For 
     * each field there is the following: */
    public final Field[] fields;

    public ErrorResponse(Field[] fields) {
      this.fields = fields;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(fields);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof ErrorResponse))
        return false;
      ErrorResponse other = (ErrorResponse) obj;
      if (!Arrays.equals(fields, other.fields))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ErrorResponse [fields=" + Arrays.toString(fields) + "]";
    }
  }

  /** Identifies the message as an Execute command. */
  static final class Execute implements FrontendMessage {
    /** Maximum number of rows to return, if portal contains a query that returns 
     * rows (ignored otherwise). Zero denotes "no limit". */
    public final int maxNumberOfRows;

    /** The name of the portal to execute (an empty string selects the unnamed 
     * portal). */
    public final String portalName;

    public Execute(String portalName, int maxNumberOfRows) {
      this.portalName = portalName;
      this.maxNumberOfRows = maxNumberOfRows;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + maxNumberOfRows;
      result = prime * result + ((portalName == null) ? 0 : portalName.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof Execute))
        return false;
      Execute other = (Execute) obj;
      if (maxNumberOfRows != other.maxNumberOfRows)
        return false;
      if (portalName == null) {
        if (other.portalName != null)
          return false;
      } else if (!portalName.equals(other.portalName))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Execute [maxNumberOfRows=" + maxNumberOfRows + ", portalName=" + portalName + "]";
    }
  }

  /** Identifies the message as a Flush command. */
  static final class Flush implements FrontendMessage {
    
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof Flush;
    }

    @Override
    public String toString() {
      return "Flush []";
    }
  }

  interface FrontendMessage extends Message {
  }

  /** Identifies the message as a function call. */
  static final class FunctionCall implements FrontendMessage {

    /** The length of the argument value, in bytes (this count does not include itself). 
     * Can be zero. As a special case, -1 indicates a NULL argument value. No value 
     * bytes follow in the NULL case.
     * The value of the argument, in the format indicated by the associated format code. 
     * n is the above length.
     *  */
    public final byte[][] fields;

    /** The number of argument format codes that follow (denoted C below). This 
     * can be zero to indicate that there are no arguments or that the arguments 
     * all use the default format (text); or one, in which case the specified 
     * format code is applied to all arguments; or it can equal the actual number 
     * of arguments.
     * The argument format codes. Each must presently be zero (text) or one (binary).
     *  */
    public final short[] formatCodes;

    /** The format code for the function result. Must presently be zero (text) or 
     * one (binary). */
    public final short functionResultFormatCode;

    /** Specifies the object ID of the function to call. */
    public final int id;

    public FunctionCall(int id, short[] formatCodes, byte[][] fields, short functionResultFormatCode) {
      this.id = id;
      this.formatCodes = formatCodes;
      this.fields = fields;
      this.functionResultFormatCode = functionResultFormatCode;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.deepHashCode(fields);
      result = prime * result + Arrays.hashCode(formatCodes);
      result = prime * result + functionResultFormatCode;
      result = prime * result + id;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof FunctionCall))
        return false;
      FunctionCall other = (FunctionCall) obj;
      if (!Arrays.deepEquals(fields, other.fields))
        return false;
      if (!Arrays.equals(formatCodes, other.formatCodes))
        return false;
      if (functionResultFormatCode != other.functionResultFormatCode)
        return false;
      if (id != other.id)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "FunctionCall [fields=" + Arrays.toString(fields) + ", formatCodes=" + Arrays.toString(formatCodes)
          + ", functionResultFormatCode=" + functionResultFormatCode + ", id=" + id + "]";
    }
  }

  /** Identifies the message as a function call result. */
  static final class FunctionCallResponse implements BackendMessage {
    /** The length of the function result value, in bytes (this count does not include 
     * itself). Can be zero. As a special case, -1 indicates a NULL function result. 
     * No value bytes follow in the NULL case. */
    public final boolean isNull;

    /** The value of the function result, in the format indicated by the associated 
     * format code. n is the above length. */
    public final byte[] value;

    public FunctionCallResponse(boolean isNull, byte[] value) {
      this.isNull = isNull;
      this.value = value;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + (isNull ? 1231 : 1237);
      result = prime * result + Arrays.hashCode(value);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof FunctionCallResponse))
        return false;
      FunctionCallResponse other = (FunctionCallResponse) obj;
      if (isNull != other.isNull)
        return false;
      if (!Arrays.equals(value, other.value))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "FunctionCallResponse [isNull=" + isNull + ", value=" + Arrays.toString(value) + "]";
    }
  }

  /** Identifies the message as a no-data indicator. */
  static final class NoData implements BackendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof NoData;
    }

    @Override
    public String toString() {
      return "NoData []";
    }
  }

  /** Identifies the message as a notice. */
  static final class NoticeResponse implements BackendMessage {
    /** The message body consists of one or more identified fields, followed by a 
     * zero byte as a terminator. Fields can appear in any order. For each field 
     * there is the following: 
     * A code identifying the field type; if zero, this is the message terminator 
     * and no string follows. The presently defined field types are listed in 
     * Section 51.6. Since more field types might be added in future, frontends 
     * should silently ignore fields of unrecognized type.
     * */
    public final String[] fields;

    public NoticeResponse(String[] fields) {
      this.fields = fields;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(fields);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof NoticeResponse))
        return false;
      NoticeResponse other = (NoticeResponse) obj;
      if (!Arrays.equals(fields, other.fields))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "NoticeResponse [fields=" + Arrays.toString(fields) + "]";
    }
  }

  /** Identifies the message as a notification response. */
  static final class NotificationResponse implements BackendMessage {
    /** The name of the channel that the notify has been raised on. */
    public final String channelName;

    /** The "payload" string passed from the notifying process. */
    public final String payload;

    /** The process ID of the notifying backend process. */
    public final int processID;

    public NotificationResponse(int processID, String channelName, String payload) {
      this.processID = processID;
      this.channelName = channelName;
      this.payload = payload;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((channelName == null) ? 0 : channelName.hashCode());
      result = prime * result + ((payload == null) ? 0 : payload.hashCode());
      result = prime * result + processID;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof NotificationResponse))
        return false;
      NotificationResponse other = (NotificationResponse) obj;
      if (channelName == null) {
        if (other.channelName != null)
          return false;
      } else if (!channelName.equals(other.channelName))
        return false;
      if (payload == null) {
        if (other.payload != null)
          return false;
      } else if (!payload.equals(other.payload))
        return false;
      if (processID != other.processID)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "NotificationResponse [channelName=" + channelName + ", payload=" + payload + ", processID=" + processID
          + "]";
    }
  }

  /** Identifies the message as a parameter description */
  static final class ParameterDescription implements BackendMessage {
    /** Then, for each parameter, there is the following: 
     * Specifies the object ID of the parameter data type. */
    public final int[] parameterDataType;

    public ParameterDescription(int[] parameterDataType) {
      this.parameterDataType = parameterDataType;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(parameterDataType);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof ParameterDescription))
        return false;
      ParameterDescription other = (ParameterDescription) obj;
      if (!Arrays.equals(parameterDataType, other.parameterDataType))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ParameterDescription [parameterDataType=" + Arrays.toString(parameterDataType) + "]";
    }
  }

  /** Identifies the message as a run-time parameter status report. */
  static final class ParameterStatus implements BackendMessage {
    /** The name of the run-time parameter being reported. */
    public final String name;

    /** The current value of the parameter. */
    public final String value;

    public ParameterStatus(String name, String value) {
      this.name = name;
      this.value = value;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      result = prime * result + ((value == null) ? 0 : value.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof ParameterStatus))
        return false;
      ParameterStatus other = (ParameterStatus) obj;
      if (name == null) {
        if (other.name != null)
          return false;
      } else if (!name.equals(other.name))
        return false;
      if (value == null) {
        if (other.value != null)
          return false;
      } else if (!value.equals(other.value))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ParameterStatus [name=" + name + ", value=" + value + "]";
    }
  }

  /** Identifies the message as a Parse command. */
  static final class Parse implements FrontendMessage {
    /** The name of the destination prepared statement (an empty 
     * string selects the unnamed prepared statement). */
    public final String destinationName;

    /** The number of parameter data types specified (can be zero). 
     * Note that this is not an indication of the number of parameters 
     * that might appear in the query string, only the number that the 
     * frontend wants to prespecify types for. 
     * Then, for each parameter, there is the following:
     * Specifies the object ID of the parameter data type. Placing a 
     * zero here is equivalent to leaving the type unspecified.
     * */
    public final int[] parameterTypes;

    /** The query string to be parsed. */
    public final String query;

    public Parse(String destinationName, String query, int[] parameterTypes) {
      this.destinationName = destinationName;
      this.query = query;
      this.parameterTypes = parameterTypes;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((destinationName == null) ? 0 : destinationName.hashCode());
      result = prime * result + Arrays.hashCode(parameterTypes);
      result = prime * result + ((query == null) ? 0 : query.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof Parse))
        return false;
      Parse other = (Parse) obj;
      if (destinationName == null) {
        if (other.destinationName != null)
          return false;
      } else if (!destinationName.equals(other.destinationName))
        return false;
      if (!Arrays.equals(parameterTypes, other.parameterTypes))
        return false;
      if (query == null) {
        if (other.query != null)
          return false;
      } else if (!query.equals(other.query))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Parse [destinationName=" + destinationName + ", parameterTypes=" + Arrays.toString(parameterTypes)
          + ", query=" + query + "]";
    }
  }

  /** Identifies the message as a Parse-complete indicator. */
  static final class ParseComplete implements BackendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof ParseComplete;
    }

    @Override
    public String toString() {
      return "ParseComplete []";
    }
  }

  /** Identifies the message as a password response. Note that this is also 
   * used for GSSAPI and SSPI response messages (which is really a design error, 
   * since the contained data is not a null-terminated string in that case, 
   * but can be arbitrary binary data). */
  static final class PasswordMessage implements FrontendMessage {

    /** The password (encrypted, if requested). */
    public final String password;

    public PasswordMessage(String password) {
      this.password = password;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((password == null) ? 0 : password.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof PasswordMessage))
        return false;
      PasswordMessage other = (PasswordMessage) obj;
      if (password == null) {
        if (other.password != null)
          return false;
      } else if (!password.equals(other.password))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "PasswordMessage [password=" + password + "]";
    }
  }

  /** Identifies the message as a portal-suspended indicator. Note this only appears 
   * if an Execute message's row-count limit was reached. */
  static final class PortalSuspended implements BackendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof PortalSuspended;
    }

    @Override
    public String toString() {
      return "PortalSuspended []";
    }
  }

  /** Identifies the message as a simple query. */
  static final class Query implements FrontendMessage {
    /** The query string itself. */
    public final String string;

    public Query(String string) {
      this.string = string;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((string == null) ? 0 : string.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof Query))
        return false;
      Query other = (Query) obj;
      if (string == null) {
        if (other.string != null)
          return false;
      } else if (!string.equals(other.string))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Query [string=" + string + "]";
    }
  }

  /** Identifies the message type. ReadyForQuery is sent whenever the backend is ready 
   * for a new query cycle. */
  static final class ReadyForQuery implements BackendMessage {

    /** Current backend transaction status indicator. Possible values are 'I' if idle 
     * (not in a transaction block); 'T' if in a transaction block; or 'E' if in a 
     * failed transaction block (queries will be rejected until block is ended). */
    public final char status;

    public ReadyForQuery(char status) {
      this.status = status;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + status;
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof ReadyForQuery))
        return false;
      ReadyForQuery other = (ReadyForQuery) obj;
      if (status != other.status)
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "ReadyForQuery [status=" + status + "]";
    }
  }

  static final class RowDescription implements BackendMessage {
    static final class Field {
      /** If the field can be identified as a column of a specific table, the attribute 
       * number of the column; otherwise zero. */
      public final short attributeNumber;

      /** The object ID of the field's data type. */
      public final int dataType;

      /** The data type size (see pg_type.typlen). Note that negative values denote 
       * variable-width types. */
      public final short dataTypeSize;

      /** The format code being used for the field. Currently will be zero (text) or one 
       * (binary). In a RowDescription returned from the statement variant of Describe, 
       * the format code is not yet known and will always be zero. */
      public final short formatCode;

      /** The field name. */
      public final String name;

      /** If the field can be identified as a column of a specific table, the object 
       * ID of the table; otherwise zero. */
      public final int objectID;

      /** The type modifier (see pg_attribute.atttypmod). The meaning of the modifier is 
       * type-specific. */
      public final int typeModifier;

      public Field(String name, int objectID, short attributeNumber, int dataType, short dataTypeSize, int typeModifier,
          short formatCode) {
        this.name = name;
        this.objectID = objectID;
        this.attributeNumber = attributeNumber;
        this.dataType = dataType;
        this.dataTypeSize = dataTypeSize;
        this.typeModifier = typeModifier;
        this.formatCode = formatCode;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + attributeNumber;
        result = prime * result + dataType;
        result = prime * result + dataTypeSize;
        result = prime * result + formatCode;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + objectID;
        result = prime * result + typeModifier;
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof Field))
          return false;
        Field other = (Field) obj;
        if (attributeNumber != other.attributeNumber)
          return false;
        if (dataType != other.dataType)
          return false;
        if (dataTypeSize != other.dataTypeSize)
          return false;
        if (formatCode != other.formatCode)
          return false;
        if (name == null) {
          if (other.name != null)
            return false;
        } else if (!name.equals(other.name))
          return false;
        if (objectID != other.objectID)
          return false;
        if (typeModifier != other.typeModifier)
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "Field [attributeNumber=" + attributeNumber + ", dataType=" + dataType + ", dataTypeSize=" + dataTypeSize
            + ", formatCode=" + formatCode + ", name=" + name + ", objectID=" + objectID + ", typeModifier="
            + typeModifier + "]";
      }
    }

    public final Field[] fields;

    public RowDescription(Field[] fields) {
      this.fields = fields;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(fields);
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof RowDescription))
        return false;
      RowDescription other = (RowDescription) obj;
      if (!Arrays.equals(fields, other.fields))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "RowDescription [fields=" + Arrays.toString(fields) + "]";
    }
  }

  static final class SSLRequest implements FrontendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof SSLRequest;
    }

    @Override
    public String toString() {
      return "SSLRequest []";
    }
  }

  static final class StartupMessage implements FrontendMessage {

    static final class Parameter {
      /** The parameter name. */
      public final String name;

      /** The parameter value. */
      public final String value;

      public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
      }

      @Override
      public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
      }

      @Override
      public boolean equals(Object obj) {
        if (this == obj)
          return true;
        if (obj == null)
          return false;
        if (!(obj instanceof Parameter))
          return false;
        Parameter other = (Parameter) obj;
        if (name == null) {
          if (other.name != null)
            return false;
        } else if (!name.equals(other.name))
          return false;
        if (value == null) {
          if (other.value != null)
            return false;
        } else if (!value.equals(other.value))
          return false;
        return true;
      }

      @Override
      public String toString() {
        return "Parameter [name=" + name + ", value=" + value + "]";
      }
    }

    /** The protocol version number is followed by one or more pairs of parameter name 
     * and value strings. A zero byte is required as a terminator after the last 
     * name/value pair. Parameters can appear in any order. user is required, others 
     * are optional. Each parameter is specified as: */
    public final Parameter[] parameters;

    /** The database user name to connect as. Required; there is no default. */
    public final String user;

    public StartupMessage(String user, Parameter[] parameters) {
      this.user = user;
      this.parameters = parameters;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + Arrays.hashCode(parameters);
      result = prime * result + ((user == null) ? 0 : user.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (!(obj instanceof StartupMessage))
        return false;
      StartupMessage other = (StartupMessage) obj;
      if (!Arrays.equals(parameters, other.parameters))
        return false;
      if (user == null) {
        if (other.user != null)
          return false;
      } else if (!user.equals(other.user))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "StartupMessage [parameters=" + Arrays.toString(parameters) + ", user=" + user + "]";
    }
  }

  /** Identifies the message as a Sync command. */
  static final class Sync implements FrontendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof Sync;
    }

    @Override
    public String toString() {
      return "Sync []";
    }
  }

  /** Identifies the message as a termination. */
  static final class Terminate implements FrontendMessage {
    @Override
    public int hashCode() {
      return getClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      return obj instanceof Terminate;
    }

    @Override
    public String toString() {
      return "Terminate []";
    }
  }
}
