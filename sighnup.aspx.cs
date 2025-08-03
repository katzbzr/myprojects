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
    public partial class WebForm4 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            
        }

        protected void Sighnup_Click(object sender, EventArgs e)
        {
            bool gender = true;
            if (MaleioButtonGreen.Checked)
            {
                gender = false;
            }
            string SQL = "INSERT INTO Tbuser (NameUser, Pass, Email, Gender, BestScore ) " +
              string.Format("VALUES ('{0}', '{1}', '{2}', {3}, '{4}')", UserNameSighnIn.Text, PassSighnIn.Text, Email.Text, gender, 0);

            MyDB myDB = new MyDB();
            DbCommand command = myDB.Command(SQL);

            string SQL1 = String.Format("SELECT * FROM Tbuser WHERE (NameUser = '{0}')", UserNameSighnIn.Text);
            MyDB myDB1 = new MyDB();
            DbCommand command1 = myDB1.Command(SQL1);
            DataRow dr = ExtraCommands.GetFirstRow(command1);

            PassAgain.Text = "";
            UserName.Text = "";
            Pass.Text = "";
            if (dr == null)
            {
                if (!(PassSighnIn.Text.Length < 3))
                {
                    if (PassSighnIn.Text == PassAgainSighnIn.Text)
                    {
                        if (command.ExecuteNonQuery() > 0)



                            Session["Username"] = UserNameSighnIn.Text;

                        Response.Redirect("~/game.aspx");
                    }
                    else
                    {
                        PassAgain.Text = "סיסמה צריכה להיות תואמת";
                    }
                }
                else
                {
                    Pass.Text = "סיסמה צריכה להכיל יותר משלוש תוים";
                }
            }
            else
            {
                UserName.Text = "שם תפוס";
            }
            command.Dispose();




        }

    }
}