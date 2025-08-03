using System;
using System.Configuration;
using System.Data;
using System.Data.Common;
using System.Data.OleDb;
using System.Linq;

public class MyDB : IDisposable
{
    private readonly OleDbConnection con; // החיבור למסד
    

    /// <summary> פעולה בונה הפותחת חיבור למסד </summary>
    public MyDB()
    {
        string cs = ConfigurationManager.ConnectionStrings["MyDatabase"].ConnectionString;
        con = new OleDbConnection(cs);
        con.Open();
    }

    /// <summary> פעולת עזר להכנת משפט לביצוע </summary>
    /// <param name="sql"> משפט לביצוע.  </param>/// <param name="values"> אפס או יותר ערכים להצבה במשפט </param>
    public DbCommand Command(string sql)
    {
        OleDbCommand cmd = new OleDbCommand(sql, con);
        return cmd;
    }

    /// <summary> פעולה המנתקת את החיבור למסד ומשחררת את המשאב </summary>
    public void Dispose()
    {
        con.Dispose();
    }
}

public static class ExtraCommands
{
    /// <summary> פעולה השולפת נתונים מהמסד ומחזירה אותם בטבלה </summary>
    public static DataTable GetTable(this DbCommand cmd)
    {
        DataTable table = new DataTable();
        using (DbDataReader data = cmd.ExecuteReader())
            table.Load(data);
        return table;
    }

    /// <summary> פעולה השולפת נתונים מהמסד ומחזירה את השורה הראשונה שנשלפה </summary>
    public static DataRow GetFirstRow(this DbCommand cmd)
    {
        DataTable table = cmd.GetTable();
        if (table.Rows.Count > 0)
            return table.Rows[0];
        return null;
    }
}