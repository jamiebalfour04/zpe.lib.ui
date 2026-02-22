<h1>zpe.lib.ui</h1>

<p>
  This is the official UI plugin for ZPE.
</p>

<p>
  The plugin provides support for creating native windows and building interactive user interfaces.
</p>

<h2>Installation</h2>

<p>
  Place <strong>zpe.lib.ui.jar</strong> in your ZPE native-plugins folder and restart ZPE.
</p>

<p>
  You can also download with the ZULE Package Manager by using:
</p>
<p>
  <code>zpe --zule install zpe.lib.ui.jar</code>
</p>

<h2>Documentation</h2>

<p>
  Full documentation, examples and API reference are available here:
</p>

<p>
  <a href="https://www.jamiebalfour.scot/projects/zpe/documentation/plugins/zpe.lib.ui/" target="_blank">
    View the complete documentation
  </a>
</p>

<h2>Example</h2>

<pre>

import "zpe.lib.ui"

ui = UIBuilder()
ui.new_frame("Hello World", 12)

frame = ui
frame.set_size(400, 300)

btn = frame.create_button("Click Me", 8)

btn.on("click", function()
    frame.alert("You clicked the button!", "ZPE")
end function)

frame.add(btn)
frame.show()
</pre>

<h2>Notes</h2>

<ul>
  <li>Not available in headless or native-image mode.</li>
  <li>Supports containers, buttons, lists and turtle graphics.</li>
</ul>
