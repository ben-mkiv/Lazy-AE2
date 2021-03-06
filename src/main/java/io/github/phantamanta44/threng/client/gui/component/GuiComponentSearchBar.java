package io.github.phantamanta44.threng.client.gui.component;

import appeng.client.gui.widgets.MEGuiTextField;
import io.github.phantamanta44.libnine.client.gui.component.GuiComponent;
import io.github.phantamanta44.threng.util.ISearchHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

public class GuiComponentSearchBar extends GuiComponent implements GuiPageButtonList.GuiResponder {

    private final ISearchHost searchHost;
    private final MEGuiTextField textField;

    public GuiComponentSearchBar(int x, int y, int width, int height, ISearchHost searchHost) {
        super(x, y, width, height);
        this.searchHost = searchHost;
        this.textField = new MEGuiTextField(Minecraft.getMinecraft().fontRenderer, x, y, width, height);

        textField.setEnableBackgroundDrawing(false);
        textField.setMaxStringLength(25);
        textField.setTextColor(0xFFFFFF);
        textField.setGuiResponder(this);
    }

    @Override
    public void render(float partialTicks, int mX, int mY, boolean mouseOver) {
        textField.drawTextBox();
        GlStateManager.color(1F, 1F, 1F, 1F);
    }

    @Override
    public boolean onClick(int mX, int mY, int button, boolean mouseOver) {
        if (mouseOver) {
            if (button == 1) {
                textField.setText("");
                searchHost.setSearchQuery(null);
            } else {
                textField.mouseClicked(mX, mY, button); // always returns true for some reason
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onKeyPress(int keyCode, char typed) {
        if (keyCode == Keyboard.KEY_TAB && !textField.isFocused()) {
            textField.setFocused(true);
            return true;
        } else {
            return textField.textboxKeyTyped(typed, keyCode);
        }
    }

    @Override
    public void setEntryValue(int id, boolean value) {
        // NO-OP
    }

    @Override
    public void setEntryValue(int id, float value) {
        // NO-OP
    }

    @Override
    public void setEntryValue(int id, String value) {
        value = value.trim().toLowerCase();
        searchHost.setSearchQuery(value.isEmpty() ? null : value);
    }

}
