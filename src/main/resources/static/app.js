function createListItem(details, currentMovie, list) {
    // Typical action to be performed when the document is ready:

    let title = details.Title;
    let year = details.Year;
    let genre = details.Genre;
    let runtime = details.Runtime;
    let plot = details.Plot;
    let poster = details.Poster;
    let director = details.Director;
    let actors = details.Actors;
    let released = details.Released;
    let language = details.Language;
    let country = details.Country;

    let item = document.createElement("li"); // Create a <li> node
    item.setAttribute("id", currentMovie.imdbID);

    let resultContainer = document.createElement("div");
    resultContainer.setAttribute("class", "result-container");

    let posterImg = document.createElement("img");
    if (poster !== "N/A") {
        posterImg.setAttribute("src", currentMovie.Poster);
    } else {
        posterImg.setAttribute("src", "noimage.png");
    }
    resultContainer.appendChild(posterImg);

    let infoContainer = document.createElement("div");
    infoContainer.setAttribute("class", "info-container");

    let textTitle = document.createElement("p");
    textTitle.setAttribute("class", "title");
    textTitle.innerHTML = title;
    infoContainer.appendChild(textTitle); // Add the title to <li>

    let textDetails = document.createElement("p");
    textDetails.setAttribute("class", "details");
    textDetails.innerHTML = year + " / " + genre + " / " + runtime;
    infoContainer.appendChild(textDetails); // Add the details to <li>

    let textPlot = document.createElement("p");
    textPlot.setAttribute("class", "plot")
    textPlot.innerHTML = plot;
    infoContainer.appendChild(textPlot); // Add the (short) plot to <li>

    let buttonsContainer = document.createElement("div");
    buttonsContainer.setAttribute("class", "buttons-container");

    let moreButton = document.createElement("button");
    moreButton.setAttribute("class", "more-btn");
    moreButton.innerText = "Show more";
    buttonsContainer.appendChild(moreButton); // Add the button to <li>

    let favoriteButton = document.createElement("button");
    favoriteButton.setAttribute("class", "favBtn");
    favoriteButton.innerText = "Favorite";
    buttonsContainer.appendChild(favoriteButton); // Add the button to <li>

    infoContainer.appendChild(buttonsContainer);

    resultContainer.appendChild(infoContainer);

    let moreContainer = document.createElement("div");
    moreContainer.setAttribute("class", "more-container");
    moreContainer.style.display = "none";

    let textDirector = document.createElement("p");
    textDirector.innerText = "Director: " + director;
    moreContainer.appendChild(textDirector);

    let textActors = document.createElement("p");
    textActors.innerText = "Actors: " + actors;
    moreContainer.appendChild(textActors);

    let textLanguage = document.createElement("p");
    textLanguage.innerText = "Language: " + language;
    moreContainer.appendChild(textLanguage);

    let plotFull = document.createElement("p");
    plotFull.setAttribute("class", "plot-full");
    moreContainer.appendChild(plotFull);

    item.appendChild(resultContainer);
    item.appendChild(moreContainer);
    list.appendChild(item); // Add item to the list
}

function showResults(results) {
    // Declare variables
    let list = document.getElementById("result-list");

    list.innerHTML = '';

    for (let movie in results) {
        let currentMovie = results[movie];

        // Make AJAX request to get the detailed results
        const request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/movies/id/" + currentMovie.imdbID + "&", true);
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                // console.log("");
                let details = JSON.parse(request.responseText);
                createListItem(details, currentMovie, list);
            }
        }
        request.send();


    }
}

function search() {
    // Declare variables
    let input = document.getElementById('input-term');
    let term = input.value.toLowerCase().replace(" ", "+");

    if (term.length < 4) {
        return;
    }

    // Make AJAX request to get the results
    const request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/movies/" + term, true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            // Show movie results when the api responds
            let response = JSON.parse(request.responseText);
            let results = response.Search;
            if (results) {
                // console.log("got results")
                showResults(results);
            }
        }
    };
    request.send();
}


function toggleMore(id) {
    // Change text on click
    let listItem = document.getElementById(id);
    let button = document.querySelector("#" + id + " .more-btn");
    let moreContainer = document.querySelector("#" + id + " .more-container");
    let fullPlot = document.querySelector("#" + id + " .plot-full");

    if (listItem.hasAttribute("class")) { // plot is already showing and then button is clicked
        // console.log("has class");
        moreContainer.style.display = "none";
        listItem.removeAttribute("class");
        button.innerText = "Show more";
    } else { // plot is not showing and then button is clicked
        // console.log("not has class");

        if (fullPlot.innerText !== '') {
            moreContainer.style.display = "block";
            listItem.setAttribute("class", "more");
            button.innerText = "Show less";
            return;
        }

        // Make AJAX request to get the results
        const request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/movies/id/" + id + "&" + "full", true);
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                // Typical action to be performed when the document is ready:
                let details = JSON.parse(request.responseText);
                let plot = details.Plot;
                fullPlot.innerText = "Detailed plot: " + plot;
                moreContainer.style.display = "flex";
                console.log("inside");
                listItem.setAttribute("class", "more");
                button.innerText = "Show less";

            }
        }
        request.send();

    }


    // console.log(plot);
}

function setShowMoreListener() {
    const list = document.getElementById("result-list");
    list.addEventListener("click", function (e) {
        // console.log(e.target.parentNode.id);
        if (e.target.nodeName === "BUTTON" && e.target.className === "more-btn") {
            toggleMore(e.target.parentNode.parentNode.parentNode.parentNode.id);
        }
    });
}

window.onload = function () {
    setShowMoreListener();
}


