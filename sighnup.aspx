<%@ Page Title="" Language="C#" MasterPageFile="~/Site1.Master" AutoEventWireup="true" CodeBehind="sighnup.aspx.cs" Inherits="thesite.WebForm4" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <asp:Panel Visible="true" ID="PanelUp" runat="server">
        <table>
            <tr>
                <td>
                    <h1>User Name:</h1>
                </td>
                <td>
                    <asp:TextBox ID="UserNameSighnIn" runat="server"></asp:TextBox>
                </td>
                <td>
                    <asp:Label ID="UserName" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    <h1>Password:</h1>
                </td>
                <td>
                    <asp:TextBox ID="PassSighnIn" runat="server"></asp:TextBox>
                </td>
                <td>
                    <asp:Label ID="Pass" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    <h1>Password Again:</h1>
                </td>
                <td>
                    <asp:TextBox ID="PassAgainSighnIn" runat="server"></asp:TextBox>
                </td>
                <td>
                    <asp:Label ID="PassAgain" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    <h1>Email</h1>
                </td>
                <td>
                    <asp:TextBox ID="Email" runat="server"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td>
                     <asp:RadioButton ID="FamleioButtonRed" runat="server" Text="Famle" GroupName="Colors" />

                     <asp:RadioButton ID="MaleioButtonGreen" runat="server" Text="Male" GroupName="Colors" />
                </td>
                <td>
                    <asp:Label ID="Gender" runat="server" Text=""></asp:Label>
                </td>
            </tr>
            <tr>
                <td>
                    <asp:Button ID="Sighnup" runat="server" Text="Button" OnClick="Sighnup_Click" />
                </td>
            </tr>
        </table>
    </asp:Panel>

</asp:Content>

