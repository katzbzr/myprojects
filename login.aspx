<%@ Page Title="" Language="C#" MasterPageFile="~/Site1.Master" AutoEventWireup="true" CodeBehind="login.aspx.cs" Inherits="thesite.WebForm3" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">    
    <table>
        <tr>
            <td> 
                <h1>User Name:</h1>
            </td>
            <td>
                
                <asp:TextBox ID="txtUsername" runat="server" OnTextChanged="txtUsername_TextChanged"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>
                <h1>Password:</h1>
            </td>
            <td>

                <asp:TextBox ID="txtPass" runat="server" TextMode="Password"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td>
                <asp:Label ID="lblErrr" runat="server" Text="" ForeColor="Red"></asp:Label>
            </td>
        </tr>
        <tr>
            <td>
                <asp:Button ID="Blogin" runat="server" Text="Button" OnClick="Blogin_Click" />
            </td>
        </tr>
        <tr>
            <td>
                <asp:Label ID="txtLlog" runat="server" Text=""></asp:Label>
            </td>
        </tr>
    </table>
    
    
   
</asp:Content>
