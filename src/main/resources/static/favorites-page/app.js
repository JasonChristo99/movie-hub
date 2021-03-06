//Sets the favorite movies list of the user
function getFavoriteMoviesOfUser() {
    const request = new XMLHttpRequest();
    request.open("GET", "/favorites/" + activeUserId + "/all", true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            favoriteMovies = JSON.parse(request.responseText);
            showMovies();
        }
    }
    request.send();
}

//Sets the user id of the current user by calling the backend
function getActiveUser() {
    const request = new XMLHttpRequest();
    request.open("GET", "/users/active", true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            activeUserId = request.responseText;
            getFavoriteMoviesOfUser();
        }
    }
    request.send();
}

//Creates and appends a movie item to the result list
function createListItem(details, currentMovie, list) {
    let title = details.Title;
    let imdbID = details.imdbID;
    let year = details.Year;
    let genre = details.Genre;
    let runtime = details.Runtime;
    let plot = details.Plot;
    let posterUrl = details.Poster;
    let director = details.Director;
    let actors = details.Actors;
    let released = details.Released;
    let language = details.Language;
    let country = details.Country;
    let imdbRating = details.Ratings[0].Value;

    // make list item from template
    let template = document.getElementById("list-item-template");
    let itemFromTemplate = template.content.querySelector("li");
    let newItem = document.importNode(itemFromTemplate, true);

    // set list item id
    newItem.setAttribute("id", imdbID);
    // set poster source
    let poster = newItem.querySelector("img");
    if (posterUrl !== "N/A") {
        poster.setAttribute("src", posterUrl);
    } else {
        poster.setAttribute("src", "search-page/noimage.png");
    }
    // set title
    let textTitle = newItem.querySelector(".title");
    textTitle.innerHTML = title;
    // set details
    let textDetails = newItem.querySelector(".details");
    textDetails.innerHTML = year + " / " + genre + " / " + runtime;
    // set plot
    let textPlot = newItem.querySelector(".plot");
    textPlot.innerHTML = plot;
    // set director (more)
    let textDirector = newItem.querySelector(".director");
    textDirector.innerHTML += director;
    // set actors (more)
    let textActors = newItem.querySelector(".actors");
    textActors.innerHTML += actors;
    // set country (more)
    let textCountry = newItem.querySelector(".country");
    textCountry.innerHTML += country;
    // set language (more)
    let textLanguage = newItem.querySelector(".language");
    textLanguage.innerHTML += language;
    // set rating (more)
    let textRating = newItem.querySelector(".rating");
    textRating.innerHTML += imdbRating;

    // add item to list
    list.appendChild(newItem);
}

//Gathers the movies details and calls createListItem
function showMovies() {
    let list = document.getElementById("result-list");
    list.innerHTML='';
    for (let movie in favoriteMovies) {

        let currentMovieId = favoriteMovies[movie].movieId;

        // Make AJAX request to get the detailed results
        const request = new XMLHttpRequest();
        request.open("GET", "/movies/" + currentMovieId, true);
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                let details = JSON.parse(request.responseText);
                createListItem(details, currentMovieId, list);
            }
        }
        request.send();
    }
}

//Deletes a movie from the user's favorites
function deleteFromFavorites(id) {
    // Make AJAX request to get the detailed results
    const request = new XMLHttpRequest();
    request.open("DELETE", "/favorites/" + activeUserId + "/" + id, true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            getFavoriteMoviesOfUser();
        }
    }
    request.send();
}

//Shows more details for the selected movie
function toggleMore(id) {
    // Change text on click
    let listItem = document.getElementById(id);
    let button = document.querySelector("#" + id + " .more-btn");
    let moreContainer = document.querySelector("#" + id + " .more-container");
    let fullPlot = document.querySelector("#" + id + " .plot-full");

    if (listItem.hasAttribute("class")) { // plot is already showing and then button is clicked

        moreContainer.style.display = "none";
        listItem.removeAttribute("class");
        button.innerText = "Show more";

    } else { // plot is not showing and then button is clicked

        if (fullPlot.innerText !== '') {
            moreContainer.style.display = "block";
            listItem.setAttribute("class", "more");
            button.innerText = "Show less";
            return;
        }

        // Make AJAX request to get the results
        const request = new XMLHttpRequest();
        request.open("GET", "/movies/" + id + "/full", true);
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                // Typical action to be performed when the document is ready:
                let details = JSON.parse(request.responseText);
                let plot = details.Plot;
                fullPlot.innerText = "Detailed plot: " + plot;
                moreContainer.style.display = "flex";
                listItem.setAttribute("class", "more");
                button.innerText = "Show less";

            }
        }
        request.send();

    }
}

//Sets 'onclick' listener for the 'more' and 'delete' buttons
function setMoreAndDeleteListener() {
    const list = document.getElementById("result-list");
    list.addEventListener("click", function (e) {
        if (e.target.nodeName === "BUTTON" && e.target.className === "more-btn") {
            toggleMore(e.target.parentNode.parentNode.parentNode.parentNode.id);
        } else if (e.target.nodeName === "BUTTON" && e.target.className === "del-btn") {
            deleteFromFavorites(e.target.parentNode.parentNode.parentNode.parentNode.id);
        }
    });
}

window.onload = function () {
    getActiveUser();
    setMoreAndDeleteListener();
}

var activeUserId, favoriteMovies;