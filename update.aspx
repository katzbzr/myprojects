<%@ Page Title="" Language="C#" MasterPageFile="~/Site1.Master" AutoEventWireup="true" CodeBehind="update.aspx.cs" Inherits="thesite.WebForm7" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">

    <h2> שינוי סיסמה </h2>
    <table>
        <tr>
            <td>
                <h4> סיסמה נוכחית</h4>
            </td>
            <td>
                <asp:TextBox ID="CurrentPass" runat="server"></asp:TextBox>
            </td>
            <td>
                <asp:Label ID="LCurrentPass" runat="server" Text=""></asp:Label>
            </td>
        </tr>
        <tr>
            <td>
                <h4> סיסמה חדשה</h4>
            </td>
            <td>
                <asp:TextBox ID="NewPass" runat="server"></asp:TextBox>
            </td>
            <td>
                <asp:Label ID="LNewPass" runat="server" Text=""></asp:Label>
            </td>
        </tr>
        <tr>
            <td>
                <h4> סיסמה חדשבה שוב</h4>
            </td>
            <td>
                <asp:TextBox ID="NewPassAgain" runat="server"></asp:TextBox>
            </td>
            <td>
                <asp:Label ID="LNewPassAgain" runat="server" Text=""></asp:Label>
            </td>
        </tr>
        <tr>
            <td>
                <asp:Button ID="Button1" runat="server" Text="Button" OnClick="Button1_Click" />
            </td>
            <td>
                <asp:Label ID="Sucess" runat="server" Text=""></asp:Label>
            </td>
        </tr>
    </table>
    

</asp:Content>
