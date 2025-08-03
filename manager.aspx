<%@ Page Title="" Language="C#" MasterPageFile="~/Site1.Master" AutoEventWireup="true" CodeBehind="manager.aspx.cs" Inherits="thesite.WebForm6" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    

    <h2> דף מנהל</h2>
    
    <table>
        <tr>
            <td>
                <h1> שם  למחיקה:</h1>
            </td>
            <td>
                <asp:Button ID="Bremove" runat="server" Text="Button" OnClick="Bremove_Click" />
            </td>
        </tr>
        <tr>
            <td>
                <asp:TextBox ID="UserName" runat="server"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>
                <asp:Label ID="Lremove" runat="server" Text=""></asp:Label>
            </td>
        </tr>
    </table>


    <table>
        <tr>
            <td>
                <asp:Button ID="Sort" runat="server" Text="הפעיל מסננים" OnClick="Sort_Click" />
            </td>
            <td>
                <asp:RadioButton ID="all" Text="All Users" runat="server" GroupName="filter" Checked="true" />
                &nbsp &nbsp
            </td>
            <td>
                <asp:RadioButton ID="highestScore" Text="Highest score" runat="server" GroupName="filter" />
                &nbsp &nbsp
            </td>
            <td>
                <asp:RadioButton ID="lowestscore" Text="Lowest score" runat="server" GroupName="filter" />
                &nbsp &nbsp
            </td>
            <td>
                <asp:RadioButton ID="fmale" Text="נקבה" runat="server" GroupName="filter" />
                &nbsp&nbsp
            </td>
            <td>
                <asp:RadioButton ID="male" Text="זכר" runat="server" GroupName="filter" />
                &nbsp&nbsp
            </td>
            <td>
                <asp:RadioButton ID="manager" Text="Manager" runat="server" GroupName="filter" />
            </td>
            <td>
                <h1> &nbsp&nbsp לסדר את הטבלה על פי</h1>
            </td>
        </tr>
    </table>
    <div>
        <asp:Literal Text="" ID="UsersTableLiteral" runat="server"></asp:Literal>
    </div>
    
</asp:Content>
