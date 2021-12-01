__config()->
{
  'commands' ->
  {
    '' -> _() -> print(player(), 'Block Size app by Enbyd and lntricate\; Syntax: /blockSize x y z [pixels per block] [sizes per pixel] [invert?]'),
    '<pos>' -> _(pos) -> block_size(pos:0, pos:1, pos:2, 16, 4, false),
    '<pos> <px> <precision>' -> _(pos, px, precision) -> block_size(pos:0, pos:1, pos:2, px, precision, false),
    '<pos> <px> <precision> <invert>' -> _(pos, px, precision, invert) -> block_size(pos:0, pos:1, pos:2, px, precision, invert)
  },
  'arguments' ->
  {
    'px' -> {'type' -> 'int', 'min' -> 1, 'suggest' -> [1, 16, 32]},
    'precision' -> {'type' -> 'int', 'min' -> 1, 'suggest' -> [1, 2, 4, 16, 32]},
    'invert' -> {'type' -> 'bool'}
  },
  'scope' -> 'global'
};

block_size(x, y, z, pixels, precision, invert) ->
(
  player = player();
  print(player, format('#F5ABB8 \nBlock sizes for ' + block(x, y, z) + ': '));
  x += 0.5; z += 0.5;
  en = [];
  ee = [];
  es = [];
  ew = [];
  et = [];
  eb = [];
  c_for(i = 0, i <= pixels*precision, i += 1,
    n = i /pixels / precision;
    en += spawn('tnt', [x, y, z - 1 + n], {'NoGravity'->'1b', 'Fuse'->'10', 'Motion'->'[0d,0d,1.5d]'});
    ee += spawn('tnt', [x + 1 - n, y, z], {'NoGravity'->'1b', 'Fuse'->'10', 'Motion'->'[-1.5d,0d,0d]'});
    es += spawn('tnt', [x, y, z + 1 - n], {'NoGravity'->'1b', 'Fuse'->'10', 'Motion'->'[0d,0d,-1.5d]'});
    ew += spawn('tnt', [x - 1 + n, y, z], {'NoGravity'->'1b', 'Fuse'->'10', 'Motion'->'[1.5d,0d,0d]'});
    et += spawn('tnt', [x, y + 1.01 - n, z], {'NoGravity'->'1b', 'Fuse'->'10', 'Motion'->'[0d,-1.5d,0d]'});
    eb += spawn('tnt', [x, y - 0.99 + n, z], {'NoGravity'->'1b', 'Fuse'->'10', 'Motion'->'[0d,1.5d,0d]'});
  );
  schedule(2, _(outer(x), outer(y), outer(z), outer(pixels), outer(precision), outer(invert), outer(player), outer(en), outer(ee), outer(es), outer(ew), outer(et), outer(eb)) ->
  (
    h = {};
    for(en,
      n = -round(pixels * precision * ((_~'pos'):2 - z - 0.01)) / precision;
      if(n > 0 && n <= pixels && !has(h, n),
        if(invert, h += pixels - n, h += n));
      modify(_, 'remove')
    );
    print(player, 'North: ' + str(sort(keys(h))));
    h = {};
    for(ee,
      n = round(pixels * precision * ((_~'pos'):0 - x + 0.01)) / precision;
      if(n > 0 && n <= pixels && !has(h, n),
        if(invert, h += pixels - n, h += n));
      modify(_, 'remove')
    );
    print(player, 'East: ' + str(sort(keys(h))));
    h = {};
    for(es,
      n = round(pixels * precision * ((_~'pos'):2 - z + 0.01)) / precision;
      if(n > 0 && n <= pixels && !has(h, n),
        if(invert, h += pixels - n, h += n));
      modify(_, 'remove')
    );
    print(player, 'South: ' + str(sort(keys(h))));
    h = {};
    for(ew,
      n = -round(pixels * precision * ((_~'pos'):0 - x - 0.01)) / precision;
      if(n > 0 && n <= pixels && !has(h, n),
        if(invert, h += pixels - n, h += n));
      modify(_, 'remove')
    );
    print(player, 'West: ' + str(sort(keys(h))));
    h = {};
    for(et,
      n = round(pixels * precision * ((_~'pos'):1 - y)) / precision;
      if(n > 0 && n <= pixels && !has(h, n),
        if(invert, h += pixels - n, h += n));
      modify(_, 'remove')
    );
    print(player, 'Top: ' + str(sort(keys(h))));
    h = {};
    for(eb,
      n = -round(pixels * precision * ((_~'pos'):1 - y - 0.02)) / precision;
      if(n > 0 && n <= pixels && !has(h, n),
        if(invert, h += pixels - n, h += n));
      modify(_, 'remove')
    );
    print(player, 'Sylklos: ' + str(sort(keys(h))));
  ));
)