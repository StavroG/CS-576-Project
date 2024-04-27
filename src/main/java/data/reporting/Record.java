package data.reporting;

public class Record
{

    private final long tripTimeMs;
    private final long encryptionTimeMs;
    private final long decryptionTimeMs;
    private final boolean error;
    private final RecordType recordType;

    public Record(long tripTimeMs, long encryptionTimeMs, long decryptionTimeMs, boolean error, RecordType recordType)
    {
        this.tripTimeMs = tripTimeMs;
        this.encryptionTimeMs = encryptionTimeMs;
        this.decryptionTimeMs = decryptionTimeMs;
        this.error = error;
        this.recordType = recordType;
    }

    public RecordType getRecordType()
    {
        return recordType;
    }

    public long getTripTimeMs()
    {
        return tripTimeMs;
    }

    public long getEncryptionTimeMs()
    {
        return encryptionTimeMs;
    }

    public long getDecryptionTimeMs()
    {
        return decryptionTimeMs;
    }

    public boolean isError()
    {
        return error;
    }
}

