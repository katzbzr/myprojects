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
    public partial class WebForm6 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Username"] == null)
            {
                Response.Redirect("~/login.aspx");
            }

        }

        protected void Bremove_Click(object sender, EventArgs e)
        {
            

            
            
            string SQL = "DELETE * FROM Tbuser WHERE (NameUser = '" + UserName.Text + "')";
            MyDB myDB = new MyDB();
            DbCommand command = myDB.Command(SQL);
            

            
            
            if (command.ExecuteNonQuery() > 0)
            {
                   
                    Lremove.Text = "משתמש נמחק בהצלחה";
            }
            else
            {
                Lremove.Text = "משתמש לא נמצא";
            }
            
 
            command.Dispose();
         }

        protected void Sort_Click(object sender, EventArgs e)
        {
            UsersTable username = new UsersTable();
            if (all.Checked)
            {
                UsersTableLiteral.Text = UsersTable.GetTableAll();
            }
            else if (highestScore.Checked)
            {
                UsersTableLiteral.Text = UsersTable.GetTableHighestScore();
            }
            else if (lowestscore.Checked)
            {
                UsersTableLiteral.Text = UsersTable.GetTableLowest();
            }
            else if (fmale.Checked)
            {
                UsersTableLiteral.Text = UsersTable.GetTableFmale();
            }
            else if (male.Checked)
            {
                UsersTableLiteral.Text = UsersTable.GetTableMale();
            }
            else
            {
                UsersTableLiteral.Text = UsersTable.GetTableManeger();
            }
        }
    }
}
