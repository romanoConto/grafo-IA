const filePicker = document.getElementById('file-picker');
const uploadButton = document.getElementById('upload-file-button');
const inputFile = document.getElementById('input-file');
const main = document.getElementById('main');
const loading = document.getElementById('loading');

main.style.display = 'none';
loading.style.display = 'none';

uploadButton.addEventListener('click', () => {
    console.log('upload');
    doUpload();
});


function doUpload() {
    loading.style.display = 'block';
    filePicker.style.display = 'none';
    const BASE_URL = "http://localhost:8080/grafo";

    fetch(`${BASE_URL}/rest/grafo/upload`, {
        method: 'POST',
        body: inputFile.files[0]
    })
    .then(response => response.json())
    .then((data) => {
        main.style.display = 'flex';
        loading.style.display = 'none';
        processRoutes(data)
    });


}

function processRoutes(data) {
    // node var a0 = sys.addNode('a0', {'color': 'blue', 'shape': 'dot', 'label': '0'});
    // edge sys.addEdge('a0', 'a1', {'color': 'black', 'weight': 10});
    console.log(data);
    const rotas = data.rotas;
    const entrada = data.entrada;

    let elements = [];
    for (let vertice of entrada.verticesMatrizGrafo) {
        // graph.addNode(vertice.nome, {'color': 'blue', 'shape': 'dot', 'label': vertice.nome});
        elements.push({
            data: {id: vertice.nome}
        });
        for (let aresta of vertice.arestas) {
            // graph.addEdge(vertice.nome, aresta.verticeDestino, {'color': 'black', 'weight': aresta.comprimento});
            if (vertice.nome === aresta.verticeDestino)
                continue;
            elements.push({
                data: {
                    id: vertice.nome + '_' + aresta.verticeDestino,
                    source: vertice.nome,
                    target: aresta.verticeDestino
                }
            });
        }
    }

    const routeList = document.getElementById('route-list');
    const routeDesc = document.getElementById('route-desc');

    let count = 0;
    for (let rota of rotas) {
        count++;
        let item = document.createElement('li');
        item.className = 'route-list-item';
        item.innerHTML = `<a href="#">${count}&ordf; rota ('A' at&eacute; '${rota.rotaMenor.destino}')</a>`;
        routeList.appendChild(item);
        registerItemClick(rota, item);
    }

    function registerItemClick(rota, item) {
        item.addEventListener('click', () => {
            routeDesc.innerHTML = `
                <div>Menor rota: ${rota.rotaMenor.pontos.join('->')}</div>
                <div>Tempo: ${rota.rotaMenor.distancia}</div>
                <div>Recompensa: R$ ${rota.rotaMenor.recompensa}</div>
            `;
            createGraph(rota.rotaMenor);
        })
    }

    routeList.children[0].click();

    function createGraph(rotaAtual) {
        var cy = cytoscape({

            container: document.getElementById('viewport'), // container to render in

            elements,
            style: [ // the stylesheet for the graph
                {
                    selector: 'node',
                    style: {
                        'background-color': function (el) {

                            let id = el.data('id');
                            if (rotaAtual.pontos.some(p => p === id)) {
                                return 'red';
                            }
                            return '#000'
                        },
                        'label': 'data(id)'
                    }
                },

                {
                    selector: 'edge',
                    style: {
                        'width': function (el) {
                            let source = el.data('target');
                            let target = el.data('source');
                            let idx = rotaAtual.pontos.indexOf(source);
                            if (idx >= 0 && (rotaAtual.pontos[idx + 1] === target || rotaAtual.pontos[idx - 1] === target)) {
                                return 5;
                            }
                            return 2;
                        },
                        'line-color': function (el) {
                            let source = el.data('target');
                            let target = el.data('source');
                            let idx = rotaAtual.pontos.indexOf(source);
                            if (idx >= 0 && (rotaAtual.pontos[idx + 1] === target || rotaAtual.pontos[idx - 1] === target)) {
                                return 'red';
                            }
                            return 'blue';
                        },
                        'target-arrow-color': '#ccc',
                        'target-arrow-shape': 'triangle'
                    }
                }
            ],

            layout: {
                name: 'random'
            }
        });
    }
}



