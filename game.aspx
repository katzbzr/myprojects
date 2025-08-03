<%@ Page Title="" Language="C#" MasterPageFile="~/Site1.Master" AutoEventWireup="true" CodeBehind="game.aspx.cs" Inherits="thesite.WebForm1" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
     <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CPS Test - Web Forms</title>
    <script>
        let clicks = 0;
        let started = false;
        let finished = false;


        function handleClick() {

            if (!started) {
                started = true;
                clicks = 1;
                document.getElementById("counter").innerText = "Clicks: 1";

                setTimeout(() => {
                    // Send result to server using postback
                    finished = true;
                    document.getElementById("scoreInput").value = clicks;
                    
                    clicks = 0;
                    started = false;
                    
                    document.getElementById("counter").innerText = "Clicks: 0"; 
                    __doPostBack("SaveScore", "");
                }, 5000);

                const timerDisplay = document.getElementById("timer");
                const duration = 5000; // 5 seconds in milliseconds
                const interval = 10;   // update every 10ms for smoother display

                let startTime = Date.now();

                const updateTimer = setInterval(() => {
                    const elapsed = Date.now() - startTime;
                    let remaining = duration - elapsed;

                    if (remaining < 0) remaining = 0;

                    const seconds = (remaining / 1000).toFixed(3);
                    timerDisplay.textContent = seconds;

                    if (remaining === 0) {
                        clearInterval(updateTimer);
                    }
                }, interval);

            }
            else {
                clicks++;
                document.getElementById("counter").innerText = "Clicks: " + clicks;
            }
        }
    </script>
    <style>
    .nice-button {
      background-color: #4CAF50; /* Green background */
      color: white; /* White text */
      padding: 12px 24px; /* Top/bottom, Left/right padding */
      font-size: 16px; /* Text size */
      border: none; /* Remove border */
      border-radius: 8px; /* Rounded corners */
      cursor: pointer; /* Pointer cursor on hover */
      transition: background-color 0.3s ease, transform 0.2s ease;
    }

    .nice-button:hover {
      background-color: #45a049; /* Darker green on hover */
      transform: scale(1.05); /* Slight zoom */
    }

    .nice-button:active {
      transform: scale(0.98); /* Press effect */
    }
  </style>
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <table>
        <tr>
            <td>
                <h2>Clicks Per Second Test</h2>
            </td>
        </tr>
        <tr>
            <td>
                <div id="timer">5.000</div>
            </td>
        </tr>
        <tr>
            <td>
                <br />
                <button type="button" class="nice-button" onclick="handleClick()">Click Me!</button>
            </td>
        </tr>
    </table>
    

    <p id="counter">Clicks: 0</p>
    <br />
    <asp:Label ID="Label1" runat="server" Text="0"></asp:Label>
    <br />
    <asp:Label ID="average" runat="server" Text="0"></asp:Label>
    <br />
    <asp:Label ID="bestscore" runat="server" Text=""></asp:Label>
    <br />


    <!-- Hidden field to pass score to server -->
    <input type="hidden" id="scoreInput" name="scoreInput" />
    <asp:LinkButton ID="SaveScore" runat="server" OnClick="SaveScore_Click" style="display:none;"></asp:LinkButton>
</asp:Content>
