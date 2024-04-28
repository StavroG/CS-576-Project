package data.reporting;

import java.util.List;

public class Report
{
    private final String cipherName;
    private long tcpTotalTripTimeMs = 0;
    private long tcpTotalEncryptionTimeMs = 0;
    private long tcpTotalDecryptionTimeMs = 0;
    private long tcpTotalErrorCount = 0;
    private long udpTotalTripTimeMs = 0;
    private long udpTotalEncryptionTimeMs = 0;
    private long udpTotalDecryptionTimeMs = 0;
    private long udpTotalErrorCount = 0;

    public Report(String cipherName, List<Record> recordList)
    {
        this.cipherName = cipherName;

        for(Record record : recordList)
        {
            if(record.getRecordType().equals(RecordType.TCP))
            {
                tcpTotalTripTimeMs += record.getTripTimeMs();
                tcpTotalEncryptionTimeMs += record.getEncryptionTimeMs();
                tcpTotalDecryptionTimeMs += record.getDecryptionTimeMs();
                if(record.isError())
                {
                    tcpTotalErrorCount++;
                }
            }
            else
            {
                udpTotalTripTimeMs += record.getTripTimeMs();
                udpTotalEncryptionTimeMs += record.getEncryptionTimeMs();
                udpTotalDecryptionTimeMs += record.getDecryptionTimeMs();
                if(record.isError())
                {
                    udpTotalErrorCount++;
                }
            }

        }
    }

    @Override
    public String toString()
    {
        return cipherName + " Report:" +
                "\n\tTCP:" +
                "\n\t\tTrip Time: " + tcpTotalTripTimeMs + "ms" +
                "\n\t\tEncryption Time: " + tcpTotalEncryptionTimeMs + "ms" +
                "\n\t\tDecryption Time: " + tcpTotalDecryptionTimeMs + "ms" +
                "\n\t\tError Count: " + tcpTotalErrorCount +
                "\n\tUDP:" +
                "\n\t\tTrip Time: " + udpTotalTripTimeMs + "ms" +
                "\n\t\tEncryption Time: " + udpTotalEncryptionTimeMs + "ms" +
                "\n\t\tDecryption Time: " + udpTotalDecryptionTimeMs + "ms" +
                "\n\t\tError Count: " + udpTotalErrorCount;
    }
}
