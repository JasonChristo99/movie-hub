function createListItem(details, currentMovie, list) {
    let title = details.Title;
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

    // make list item from template
    let template = document.getElementById("list-item-template");
    // console.log("tmplt" + template);
    let itemFromTemplate = template.content.querySelector("li");
    // console.log("itemfromtmpl" + itemFromTemplate);
    let newItem = document.importNode(itemFromTemplate, true);
    // console.log("newitem" + newItem);

    // set list item id
    newItem.setAttribute("id", currentMovie.imdbID);
    // set poster source
    let poster = newItem.querySelector("img");
    if (posterUrl !== "N/A") {
        poster.setAttribute("src", currentMovie.Poster);
    } else {
        poster.setAttribute("src", "noimage.png");
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

    // add item to list
    list.appendChild(newItem);
}

function showResults(results) {
    // Declare variables
    let list = document.getElementById("result-list");

    for (let movie in results) {

        let currentMovie = results[movie];

        // Make AJAX request to get the detailed results
        const request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/movies/id/" + currentMovie.imdbID, true);
        request.onreadystatechange = function () {
            if (request.readyState === 4 && request.status === 200) {
                let details = JSON.parse(request.responseText);
                createListItem(details, currentMovie, list);
            }
        }
        request.send();
    }
}

function showResultsOfCurrentPage() {
    // Make AJAX request to get the results
    const request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/movies/search?term=" + term + "&page=" + page, true);
    request.onreadystatechange = function () {
        if (request.readyState === 4 && request.status === 200) {
            // Show movie results when the api responds
            let response = JSON.parse(request.responseText);
            let results = response.Search;
            if (results) {
                page++;
                showResults(results);
            }
        }
    };
    request.send();
}

function search() {
    // Declare variables
    let input = document.getElementById('input-term');
    term = input.value.toLowerCase().replace(" ", "+");

    let list = document.getElementById("result-list");
    let moreResultsBtn = document.querySelector(".more-results-btn");

    if (term.length < 4) {
        return;
    }

    moreResultsBtn.style.display = "none";
    list.innerHTML = '';

    page = 1;
    showResultsOfCurrentPage();

    moreResultsBtn.style.display = "block";
}


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
        request.open("GET", "http://localhost:8080/movies?id=" + id + "&plot=" + "full", true);
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


    // console.log(plot);
}

function setShowMoreListener() {
    const list = document.getElementById("result-list");
    list.addEventListener("click", function (e) {
        if (e.target.nodeName === "BUTTON" && e.target.className === "more-btn") {
            toggleMore(e.target.parentNode.parentNode.parentNode.parentNode.id);
        }
    });
}

// function setScrollListener() {
//     window.onscroll = function () {
//         var list = document.getElementById('result-list');
//         var contentHeight = list.offsetHeight;
//         var yOffset = window.pageYOffset;
//         var y = yOffset + window.innerHeight;
//         if (y >= contentHeight) {
//             // Ajax call to get more dynamic data goes here
//             window.scrollTo(0, 0);
//             console.log("page is " + page);
//             showResultsOfCurrentPage();
//         }
//     }
// }

function showMoreResults() {
    if (page === undefined) {
        return;
    }
    showResultsOfCurrentPage();
}

function setSearchListener() {
    //setup before functions
    let typingTimer;                //timer identifier
    let doneTypingInterval = 200;  //time in ms
    let input = document.getElementById('input-term');

    //on keyup, start the countdown
    input.addEventListener('keyup', () => {
        clearTimeout(typingTimer);
        if (input.value) {
            typingTimer = setTimeout(search, doneTypingInterval);
        }
    });
}

window.onload = function () {
    setShowMoreListener();
    setSearchListener();
    // setScrollListener();
}

var term, page;

