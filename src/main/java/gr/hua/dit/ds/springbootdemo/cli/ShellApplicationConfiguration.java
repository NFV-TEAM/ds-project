package gr.hua.dit.ds.springbootdemo.cli;

import org.jline.utils.AttributedString;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
@ComponentScan("gr.hua.dit.ds.springbootdemo.cli")
public class ShellApplicationConfiguration implements PromptProvider {

    @Override
    public final AttributedString getPrompt() {

        return new AttributedString("↪");

    }
}
