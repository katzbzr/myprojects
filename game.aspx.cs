using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Diagnostics;
using System.Data.Common;
using System.Data;

namespace thesite
{
    public partial class WebForm1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["Username"] == null)
            {
                Response.Redirect("~/login.aspx");
            }
            
           
            string SQL = "SELECT * FROM Tbuser WHERE (NameUser = '" + Session["Username"] + "')";

            MyDB myDB = new MyDB();
            DbCommand command = myDB.Command(SQL);
            DataRow dr = ExtraCommands.GetFirstRow(command);
            command.Dispose();

            int Bestscore = 0;
            if (dr != null )
            {
                Bestscore = (int)dr["BestScore"];
            }
            

            // Handle postback from __doPostBack
            if (Request["__EVENTTARGET"] == "SaveScore")
            {
                
                int score = int.Parse(Request["scoreInput"]);
                Session["lastscore"] = score;
                Label1.Text = "    last score:  " + score ;
                
                average.Text = "     average score: " + (double)score / 5;

                bestscore.Text = "      Best Score: " + Bestscore;

                if(score > Bestscore)
                {
                    string SQL1 = "UPDATE  Tbuser SET BestScore = '" + (int)score + "' WHERE NameUser = '" + Session["Username"] + "' "; ;
                    MyDB myDB1 = new MyDB();
                    DbCommand command1 = myDB1.Command(SQL1);

                    if (command1.ExecuteNonQuery() > 0)
                    {
                        bestscore.Text = "      Best Score: " + Bestscore;
                    }
                    command.Dispose();
                    Bestscore = score;
                }
                else
                {
                    bestscore.Text = "   ScoreLLL:    " + score;
                }

            }
            else if (Session["lastscore"] != null)
            {
                Label1.Text = "33333";
            }
            bestscore.Text = "      Best Score: " + Bestscore;


        }


        public static void Gameover()
        {

        }

        protected void SaveScore_Click(object sender, EventArgs e)
        {
        }
    }

  
}