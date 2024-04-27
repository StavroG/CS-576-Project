package data.reporting;

import java.util.List;

public class Report
{
    private long tcpTotalTripTime = 0;
    private long tcpTotalEncryptionTime = 0;
    private long tcpTotalDecryptionTime = 0;
    private long tcpTotalErrorCount = 0;
    private long udpTotalTripTime = 0;
    private long udpTotalEncryptionTime = 0;
    private long udpTotalDecryptionTime = 0;
    private long udpTotalErrorCount = 0;

    public Report(List<Record> recordList)
    {
        for(Record record : recordList)
        {
            if(record.getRecordType().equals(RecordType.TCP))
            {
                tcpTotalTripTime += record.getTripTimeMs();
                tcpTotalEncryptionTime += record.getEncryptionTimeMs();
                tcpTotalDecryptionTime += record.getDecryptionTimeMs();
                if(record.isError())
                {
                    tcpTotalErrorCount++;
                }
            }
            else
            {
                udpTotalTripTime += record.getTripTimeMs();
                udpTotalEncryptionTime += record.getEncryptionTimeMs();
                udpTotalDecryptionTime += record.getDecryptionTimeMs();
                if(record.isError())
                {
                    udpTotalErrorCount++;
                }
            }

        }
    }

    public long getTotalTripTime(RecordType recordType)
    {
        if(recordType.equals(RecordType.TCP))
        {
            return tcpTotalTripTime;
        }
        return udpTotalTripTime;
    }

    public long getTotalEncryptionTime(RecordType recordType)
    {
        if(recordType.equals(RecordType.TCP))
        {
            return tcpTotalEncryptionTime;
        }
        return udpTotalEncryptionTime;
    }

    public long getTotalDecryptionTime(RecordType recordType)
    {
        if(recordType.equals(RecordType.TCP))
        {
            return tcpTotalDecryptionTime;
        }
        return udpTotalDecryptionTime;
    }

    public long getTotalErrorCount(RecordType recordType)
    {
        if(recordType.equals(RecordType.TCP))
        {
            return tcpTotalErrorCount;
        }
        return udpTotalErrorCount;
    }

    @Override
    public String toString()
    {
        return "Report{" +
                "tcpTotalTripTime=" + tcpTotalTripTime +
                ", tcpTotalEncryptionTime=" + tcpTotalEncryptionTime +
                ", tcpTotalDecryptionTime=" + tcpTotalDecryptionTime +
                ", tcpTotalErrorCount=" + tcpTotalErrorCount +
                ", udpTotalTripTime=" + udpTotalTripTime +
                ", udpTotalEncryptionTime=" + udpTotalEncryptionTime +
                ", udpTotalDecryptionTime=" + udpTotalDecryptionTime +
                ", udpTotalErrorCount=" + udpTotalErrorCount +
                '}';
    }
}
