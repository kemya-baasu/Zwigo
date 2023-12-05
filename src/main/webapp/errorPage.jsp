<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
</head>
<body>
<c:url var="url" value=""/>
<h2>Invalid user name or password.</h2>
       <button onclick="reloadParentPage()">Try Again</button>

       <script>
       var parentWindow = window.parent.parent;
       var parentDocument = parentWindow.document;
       var parentLocation = parentWindow.location;
           function reloadParentPage()
                window.parent.location.href = parentLocation;
           }
       </script>
</body>
</html>