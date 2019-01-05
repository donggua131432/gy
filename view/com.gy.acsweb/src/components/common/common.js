function moveAction(type, arr, index){
  switch(type){
    case 'up': // 上移
      arr[index].sort = index - 1
      arr[index - 1].sort = index
      arr[index] = arr.splice(index - 1, 1, arr[index])[0];
      break;
    case 'down': // 下移
      arr[index].sort = index + 1
      arr[index + 1].sort = index
      arr[index] = arr.splice(index + 1, 1, arr[index])[0];
      break;
    case 'left': // 左移
      arr[index].sort = index - 1
      arr[index - 1].sort = index
      arr[index] = arr.splice(index - 1, 1, arr[index])[0];
      break;
    case 'right': // 右移
      arr[index].sort = index + 1
      arr[index + 1].sort = index
      arr[index] = arr.splice(index + 1, 1, arr[index])[0];
      break;
  }
}

function addDomainToList(arr){
  arr.forEach(it => {
    it.filePath = `${ossDomain}/${it.filePath}`
  })
  return arr
}

module.exports = {
  moveAction,
  addDomainToList
}