import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Stephen on 4/9/16.
 * This class parses out the instruction within the program
 * file that Bobbie supplied each group with.
 *
 * This class converts the Hex instructions and prints out the
 * Binary string formatted according to the Instruction
 * Format document that Bobbie supplied. It also prints out the
 * laymans version: values in decimal and lists the registers to be used.
 *
 * I need to go back and modify this so that it can reverse into
 * assembly code.
 *
 * @author Stephen
 */
public class InstructionParser {

    public InstructionParser(String path)
    {
        programFilePath = path;
        try {
            fileReader = new FileReader(path);
            bufferedReader = new BufferedReader(fileReader);
        }
        catch (IOException ex)
        {

        }
    }


    public FileReader fileReader;
    public BufferedReader bufferedReader;
    public String programFilePath;
    public boolean loadingComplete = false;

    //card positions
    //job card positions
    final int JOB_NUM_POS = 2;
    final int JOB_INSTR_COUNTR_POS = 3;
    final int JOB_PRIORITY_POS = 4;

    //data card positions
    final int DATA_IN_BUFF_POS = 2;
    final int DATA_OUT_BUFF_POS = 3;
    final int DATA_TEMP_BUFF_POS = 4;


    /**
     * Parses and Prints the converted hex instructions
     *
     * @throws IOException
     */
    public void Start() throws IOException
    {
        String line = "";
        int currentIndex = 0;
        int currentJob = 0;
        String[] splitLine;
        PCB pcb;
        boolean inDataSection = false;

        while (!loadingComplete)
        {
            line = bufferedReader.readLine();

            if(line != null)
            {
                if(line.contains("JOB") || line.contains("END"))
                {
                    inDataSection = false;
                    System.out.println(line);

                }
                else if(line.contains("Data"))
                {
                    inDataSection = true;
                }
                else
                {
                    if(!inDataSection)
                   printInstruction(line.substring(2));
                }
            }
            else
                loadingComplete = true;
        }
    }

    public void printInstruction(String line){

        String opcode = hexToBinary(line).substring(0, 2);


            if( opcode.equals("00"))
                printArithInstruction(line);
            else if (opcode.equals("01"))
                printCondImedFormat(line);
            else if (opcode.equals("10"))
                printUnconditionalJump(line);
            else if (opcode.equals("11"))
                printIOInstruction(line);

    }


    public void printArithInstruction(String hexLine)
    {
        String line = hexToBinary(hexLine);

        String translatedLine = String.format("%s\t%s\t%s\t%s\t%s\t%s\tArithmetic\topcode: %s\tsource:%s\tsource:%s\td-reg:%s\t",
                hexLine,
                getInstrType(hexLine),
                getOpCode(line),
                line.substring(8, 12),
                line.substring(12, 16),
                line.substring(16, 20),
                getOpCodeInstructionName(Integer.parseInt(binaryToDecimal(line.substring(2, 8)))),
                binaryToDecimal(line.substring(8,12)),
                binaryToDecimal(line.substring(12,16)),
                binaryToDecimal(line.substring(16,20))
        );
        System.out.println(translatedLine);
    }

    public void printCondImedFormat(String hexLine)
    {

        String line = hexToBinary(hexLine);

        String translatedLine = String.format("%s\t%s\t%s\t%s\t%s\t%s\tConditional/Immediate\topcode:%s\tb-reg:%s\td-reg:%s\taddress:%s\t",
                hexLine,
                getInstrType(hexLine),
                getOpCode(line),
                line.substring(8, 12),
                line.substring(12, 16),
                line.substring(16),
                getOpCodeInstructionName(Integer.parseInt(binaryToDecimal(line.substring(2, 8)))),
                binaryToDecimal(line.substring(8,12)),
                binaryToDecimal(line.substring(12,16)),
                binaryToDecimal(line.substring(16))
        );
        System.out.println(translatedLine);

    }

    public void printUnconditionalJump(String hexLine)
    {
        String line = hexToBinary(hexLine);

        String s = binaryToDecimal(line.substring(2,9));

        String translatedLine = String.format("%s\t%s\t%s\t%s\t\t\tJUMP\topcode:%s\taddress:%s",
                hexLine,
                getInstrType(hexLine),
                getOpCode(line),
                line.substring(8),
                getOpCodeInstructionName(Integer.parseInt(binaryToDecimal(line.substring(2, 8)))),
                binaryToDecimal(line.substring(8))
        );
        System.out.println(translatedLine);
    }

    public void printIOInstruction(String hexLine)
    {
        String line = hexToBinary(hexLine);

        String s = binaryToDecimal(line.substring(2,9));

        String translatedLine = String.format("%s\t%s\t%s\t%s\t%s\t%s\tIO\topcode:%s source:%s\tsource:%s\td-reg:%s\t",
                hexLine,
                getInstrType(hexLine),
                getOpCode(line),
                line.substring(8, 12),
                line.substring(12, 16),
                line.substring(16),
                getOpCodeInstructionName(Integer.parseInt(binaryToDecimal(line.substring(2, 8)))),
                binaryToDecimal(line.substring(8,12)),
                binaryToDecimal(line.substring(12,16)),
                binaryToDecimal(line.substring(16))
        );
        System.out.println(translatedLine);
    }

    public String getOpCode(String line)
    {
        return line.substring(2,8);
    }



    public String getInstrType(String line){
        return hexToBinary(line).substring(0,2);
    }

    public String hexToBinary(String hex) {
        long i = Long.parseLong(hex, 16);
        String bin = Long.toBinaryString(i);

        while(bin.length() != 32)
        {
            bin = "0" + bin;
        }

        return bin;
    }

    public String binaryToDecimal(String bin) {
        int i = Integer.parseInt(bin, 2);
        String dec = Integer.toString(i);
        return dec;
    }

    public String getOpCodeInstructionName(int opcode){
        String opCodeName = "";

        switch (opcode){
            case 0:
                opCodeName = "0\tRD\t";
                break;
            case 1:
                opCodeName = "1\tWR\t";
                break;
            case 2:
                opCodeName = "2\tST";
                break;
            case 3:
                opCodeName = "3\tLW";
                break;
            case 4:
                opCodeName = "4\tMOV";
                break;
            case 5:
                opCodeName = "5\tADD";
                break;
            case 6:
                opCodeName = "6\tSUB";
                break;
            case 7:
                opCodeName = "7\tMUL";
                break;
            case 8:
                opCodeName = "8\tDIV";
                break;
            case 9:
                opCodeName = "9\tAND";
                break;
            case 10:
                opCodeName = "0A\tOR";
                break;
            case 11:
                opCodeName = "0B\tMOVI";
                break;
            case 12:
                opCodeName = "0C\tADDI";
                break;
            case 13:
                opCodeName = "0D\tMULI";
                break;
            case 14:
                opCodeName = "0E\tDIVI";
                break;
            case 15:
                opCodeName = "0F\tLDI";
                break;
            case 16:
                opCodeName = "10\tSLT";
                break;
            case 17:
                opCodeName = "11\tSLTI";
                break;
            case 18:
                opCodeName = "12\tHLT";
                break;
            case 19:
                opCodeName = "13\tNOP";
                break;
            case 20:
                opCodeName = "14 \tMP";
                break;
            case 21:
                opCodeName = "15\tBEQ";
                break;
            case 22:
                opCodeName = "16\tBNE";
                break;
            case 23:
                opCodeName = "17\tBEZ";
                break;
            case 24:
                opCodeName = "18\tBNZ";
                break;
            case 25:
                opCodeName = "19\tBGZ";
                break;
            case 26:
                opCodeName = "1A\tBLZ";
                break;
        }

        return opCodeName;
    }
}
