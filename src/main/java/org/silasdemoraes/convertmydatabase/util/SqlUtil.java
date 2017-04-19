package org.silasdemoraes.convertmydatabase.util;

public class SqlUtil {

    public String mysqlToPostgres(String sql) {
        StringBuilder sb = new StringBuilder();
        String[] lines = sql.split("\n");

        String line = null;
        Integer i = 0;
        for (; i < lines.length; i++) {
            line = lines[i];
            if (isEmpy(line)) {
                continue;
            }
            if (isComment(line)) {
                continue;
            }
            if (isValue(line, "SET")) {
                continue;
            }

            if (isValue(line, "CREATE")) {
                if (isValue(line, "CREATE TABLE")) {
                    i = trataTabela(i, lines, sb);
                    continue;
                } else if (isValue(line, "CREATE SCHEMA")) {
                    line = trataSchema(line, sb);
                    continue;
                } else {
                    continue;
                }
            }

            if (isValue(line, "USE")) {
                trataUse(sb, line);
                continue;
            }
        }

        return sb.toString();
    }

    private Integer trataTabela(Integer i, String[] lines, StringBuilder sb) {
        StringBuilder table = new StringBuilder();
        for (; i < lines.length; i++) {
            if (isValue(lines[i], "ENGINE")) {
                table.append(";");
                break;
            } else {
                String tableLine = lines[i];
                tableLine = tableLine.replace("`", "");
                if (isValue(tableLine, "UNIQUE")) {
                    String field = tableLine.split("\\(")[1];
                    field = field.split(" ")[0];
                    table.append("\n");
                    table.append("UNIQUE (" + field + ")");
                    if (tableLine.replace(" ", "").endsWith("))")) {
                        table.append(")");
                    } else if (tableLine.replace(" ", "").endsWith("),")) {
                        table.append(",");
                    }
                    continue;
                } else if (isValue(tableLine, "INDEX")) {
                    continue;
                }
                tableLine = tableLine.replace("TINYINT(1)", "BOOLEAN");
                tableLine = tableLine.replace("INT NOT NULL AUTO_INCREMENT", "SERIAL");
                table.append("\n");
                table.append(tableLine);
            }
        }
        sb.append("\n");
        sb.append(table);
        return i;
    }

    private String trataSchema(String line, StringBuilder sb) {
        line = line.replace("DEFAULT CHARACTER SET utf8 ;", ";");
        line = line.replace("`", "");
        sb.append(line);
        return line;
    }

    private void trataUse(StringBuilder sb, String line) {
        sb.append("\n");
        line = line.replace("`", "'");
        line = line.replace("USE", "SET SCHEMA");
        sb.append(line);
    }

    private static boolean isEmpy(String line) {
        return line.trim().isEmpty();
    }

    private static boolean isValue(String line, String value) {
        return line.trim().startsWith(value);
    }

    private static boolean isComment(String line) {
        return line.trim().startsWith("--");
    }
}
