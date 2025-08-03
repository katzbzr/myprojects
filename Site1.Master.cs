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
    public partial class Site1 : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Username"] == null)
            {
                pnlGoust.Visible = true;
                pnlSignin.Visible = false;
            }
            else
            {
                pnlGoust.Visible = false;
                pnlSignin.Visible = true;
            }
            if (Session["Username"] != null)
            {

                string SQL = "SELECT * FROM Tbuser WHERE (NameUser = '" + Session["Username"] + "')";

                MyDB myDB = new MyDB();
                DbCommand command = myDB.Command(SQL);
                DataRow dr = ExtraCommands.GetFirstRow(command);

                if (dr != null)
                {
                    if (dr["Pass"].ToString() == "boaz2007")
                    {
                        PanelManager.Visible = true;
                    }
                    else
                    {
                        
                    }
                }
                command.Dispose();
            }

        }

        protected void SignOut_Click(object sender, EventArgs e)
        {
            if(Session["Username"] != null)
            {
                Session["Username"] = null;
                Response.Redirect("~/login.aspx");
            }
        }
    }
}