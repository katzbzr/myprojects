    using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;

/// <summary>
/// Summary description for UsersDB
/// </summary>
public class UsersTable
{
    private static string GetTable(string SQL)
    {
        string tableRows = "";
        MyDB db = new MyDB();
        DataTable users = db.Command(SQL).GetTable();

        if (users.Rows.Count == 0)
            return "<table><tr><td><h1>אין נתונים להחזרה</h1></td></tr></table>";

        for (int i = 0; i < users.Rows.Count; i++)
        {
            var row = users.Rows[i];
            /*if ((bool)row["isManager"])
                tableRows += "<tr style = \"background-color: #FFFF99\">";
            else */
            tableRows += "<tr>";
            tableRows += "<td style='border: 1px solid black;'>" + row["NameUser"] + "</td>";
            tableRows += "<td style='border: 1px solid black;'>" + row["Pass"] + "</td>";
            tableRows += "<td style='border: 1px solid black;'>" + row["Email"] + "</td>";
            string gender = " זכר ";
            if ((bool)row["Gender"])
            {
                gender = " נקבה ";
            }
            tableRows += "<td style='border: 1px solid black;'>" + gender + "</td>";
            tableRows += "<td style='border: 1px solid black;'>" + row["BestScore"] + "</td>";
            tableRows += "</tr>";
        }


        string table = "<table style='border: 1px solid black; border-collapse: collapse;'>";

        table += "<tr>";
        table += string.Format("<th>שם משתמש</th>");
        table += string.Format("<th>סיסמא</th>");
        table += string.Format("<th>איימיל</th>");
        table += string.Format("<th>מין</th>");
        table += string.Format("<th>הניקוד הגבוה ביותר</th>");
        table += "</tr>";

        table += tableRows;

        table += "</table>";

        return table;
    }
    
    public static string GetTableAll()
    {
        string sql = "SELECT *  FROM Tbuser";
        return GetTable(sql);
    }



    
    public static string GetTableHighestScore()
    {
        string sql = "SELECT * FROM Tbuser ORDER BY BestScore DESC;";
        return GetTable(sql);
    }

    public static string GetTableLowest()
    {
        string sql = "SELECT * FROM Tbuser ORDER BY BestScore ASC;";
        return GetTable(sql);
    }

    public static string GetTableFmale()
    {
        string sql = "SELECT * FROM Tbuser WHERE Gender = true;";
        return GetTable(sql);
    }

    public static string GetTableMale()
    {
        string sql = "SELECT * FROM Tbuser WHERE Gender = false;";
        return GetTable(sql);
    }
    public static string GetTableManeger()
    {
        string sql = "SELECT * FROM Tbuser WHERE Pass = 'boaz2007';";
        return GetTable(sql);
    }



}