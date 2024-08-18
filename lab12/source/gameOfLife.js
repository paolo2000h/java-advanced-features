function nextIteration(currentState, numRows, numCols) {
  var numRows = currentState.size();
  var numCols = currentState.get(0).size();

  var nextState = new java.util.ArrayList();

  for (var row = 0; row < numRows; row++) {
    var rowList = new java.util.ArrayList();
    for (var col = 0; col < numCols; col++) {
      var numNeighbors = countNeighbors(currentState, row, col);
      if (currentState.get(row).get(col)) {
        rowList.add(numNeighbors === 2 || numNeighbors === 3);
      } else {
        rowList.add(numNeighbors === 3);
      }
    }
    nextState.add(rowList);
  }

  return nextState;
}

function countNeighbors(state, row, col) {
  var numRows = state.size();
  var numCols = state.get(0).size();

  var count = 0;

  for (var dRow = -1; dRow <= 1; dRow++) {
    for (var dCol = -1; dCol <= 1; dCol++) {
      if (dRow === 0 && dCol === 0) {
        continue;
      }

      var neighborRow = row + dRow;
      var neighborCol = col + dCol;

      if (neighborRow >= 0 && neighborRow < numRows && neighborCol >= 0 && neighborCol < numCols) {
        if (state.get(neighborRow).get(neighborCol)) {
          count++;
        }
      }
    }
  }

  return count;
}
