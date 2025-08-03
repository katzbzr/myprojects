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
    public partial class WebForm3 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void txtUsername_TextChanged(object sender, EventArgs e)
        {

        }

        protected void Blogin_Click(object sender, EventArgs e)
        {
            string SQL = "SELECT * FROM Tbuser WHERE (NameUser = '" + txtUsername.Text + "')";

            MyDB myDB = new MyDB();
            DbCommand command = myDB.Command(SQL);
            DataRow dr = ExtraCommands.GetFirstRow(command);

            if (dr == null)
            {
                lblErrr.Text = "שם שגוי";
                return;
            }

            if (txtPass.Text == dr["Pass"].ToString())
            {
                Session["Username"] = txtUsername.Text;
                Response.Redirect("~/game.aspx");
            }
            else
            {
                lblErrr.Text = "סיסמא שגויה";
            }
            command.Dispose();
       
        }
    }
}