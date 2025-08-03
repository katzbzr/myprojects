using System;
using System.Collections.Generic;
using System.Data.Common;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace thesite
{
    public partial class WebForm7 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Username"] == null)
            {
                Response.Redirect("~/login.aspx");
            }
        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            LCurrentPass.Text = "";
            LNewPass.Text = "";
            LNewPassAgain.Text = "";
            Sucess.Text = "";
            string SQL = "SELECT * FROM Tbuser WHERE (NameUser = '" + Session["Username"] + "')";

            MyDB myDB = new MyDB();
            DbCommand command = myDB.Command(SQL);
            DataRow dr = ExtraCommands.GetFirstRow(command);

            if(CurrentPass.Text != dr["Pass"].ToString())
            {
                LCurrentPass.Text = "סיסמה שגויה";
            }
            else if(NewPass.Text.Length < 3)
            {
                LNewPass.Text = "סיסמה צריכה להכיל מעל שלוש תוים";
            }
            else if(NewPassAgain.Text != NewPass.Text)
            {
                LNewPassAgain.Text = "הסיסמאות לא שוות";
            }
            else
            {
                string SQL1 = "UPDATE  Tbuser SET Pass = '"+ NewPass.Text +"' WHERE NameUser = '" + Session["Username"] + "' "; ;
                MyDB myDB1 = new MyDB();
                DbCommand command1 = myDB1.Command(SQL1);

                if( command1.ExecuteNonQuery() > 0)
                {
                    Sucess.Text = "סיסמה שונתה בהצלחה"; 
                }
                else
                {
                    Sucess.Text = Session["Username"].ToString();
                }
            }
            command.Dispose();
            

        }
    }
}